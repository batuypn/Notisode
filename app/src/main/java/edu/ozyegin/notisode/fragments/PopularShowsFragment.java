package edu.ozyegin.notisode.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ozyegin.notisode.R;
import edu.ozyegin.notisode.ShowActivity;
import edu.ozyegin.notisode.adapters.ShowAdapter;
import edu.ozyegin.notisode.listeners.OnItemClickListener;
import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 3.6.2015.
 */
public class PopularShowsFragment extends Fragment {
    public static SparseArray<Bitmap> photoCache = new SparseArray<>(1);
    private RecyclerView mImageRecycler;
    private ShowAdapter mShowAdapter;
    private ArrayList<Show> mShows;
    private ArrayList<Show> mCurrentShows;
    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {

        @Override
        public void onClick(View v, int position) {

            Show selectedShow = mCurrentShows.get(position);

            Intent showIntent = new Intent(getActivity(), ShowActivity.class);
            showIntent.putExtra("show", selectedShow);
            showIntent.putExtra("position", position);

            ImageView coverImage = (ImageView) v.findViewById(R.id.item_image_img);
            if (coverImage == null) {
                coverImage = (ImageView) ((View) v.getParent()).findViewById(R.id.item_image_img);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                if (coverImage.getParent() != null) {
                    ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
                }
            }

            if (coverImage != null && coverImage.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap(); //ew
                if (bitmap != null && !bitmap.isRecycled()) {
                    photoCache.put(position, bitmap);

                    // Setup the transition to the detail activity
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), coverImage, "imageCover");

                    ActivityCompat.startActivity(getActivity(), showIntent, options.toBundle());
                }
            }
        }
    };

    public PopularShowsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_popular_shows, container, false);
        mImageRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_last_images_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mImageRecycler.setLayoutManager(gridLayoutManager);
        mImageRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mShowAdapter = new ShowAdapter();
        mShowAdapter.setOnItemClickListener(recyclerRowClickListener);
        mImageRecycler.setAdapter(mShowAdapter);

        new HttpRequestTask().execute();

        return rootView;
    }

    /**
     * a small helper class to update the adapter
     *
     * @param shows
     */
    private void updateAdapter(ArrayList<Show> shows) {
        mCurrentShows = shows;
        mShowAdapter.updateData(mCurrentShows);
        mImageRecycler.scrollToPosition(0);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Show[]> {
        @Override
        protected Show[] doInBackground(Void... params) {
            try {
                final String url = "https://api-v2launch.trakt.tv/shows/popular?extended=full,images";

                // Set the Accept header
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);
                requestHeaders.add("trakt-api-version", getString(R.string.trakt_api_version));
                requestHeaders.add("trakt-api-key", getString(R.string.client_id));
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Make the HTTP GET request, marshaling the response from JSON to an array of Events
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                ResponseEntity<Show[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Show[].class);
                Show[] shows = responseEntity.getBody();

                return shows;
            } catch (Exception e) {
                Log.e("PopularShowFragment", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Show[] shows) {
            mShows = new ArrayList<Show>(Arrays.asList(shows));
            updateAdapter(mShows);
        }

    }
}
