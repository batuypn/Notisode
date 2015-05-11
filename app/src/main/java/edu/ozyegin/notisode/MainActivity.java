package edu.ozyegin.notisode;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


import edu.ozyegin.notisode.listeners.OnShowCardClickListener;
import edu.ozyegin.notisode.objects.Show;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new HttpRequestTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Show[] shows) {

            //Set the arrayAdapter
            ArrayList<Card> cards = new ArrayList<Card>();

            CardGridStaggeredArrayAdapter mCardArrayAdapter = new CardGridStaggeredArrayAdapter(MainActivity.this, cards);

            for (int i = 0; i < shows.length; i++) {
                final Show show = shows[i];
                // Set supplemental actions as icon
//                ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();
//
//                IconSupplementalAction t1 = new IconSupplementalAction(MainActivity.this, R.id.ic1);
//                t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
//                    @Override
//                    public void onClick(Card card, View view) {
//                        Toast.makeText(MainActivity.this, "Add to Favorites", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                actions.add(t1);
                MaterialLargeImageCard mCard =
                        MaterialLargeImageCard.with(MainActivity.this)
                                .setTitle(show.getTitle())
                                .setSubTitle(Integer.toString(show.getYear()) + ", " + show.getGenres()[0])
                                .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                                    @Override
                                    public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                                        Picasso.with(MainActivity.this).setIndicatorsEnabled(true);  //only for debug tests
                                        Picasso.with(MainActivity.this)
                                                .load(show.getImages().getPoster().getThumb())
                                                .error(R.drawable.card_undo)
                                                .into((ImageView) viewImage);

                                    }
                                })
//                                .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large_icon, actions)
                                .build();

                mCard.setOnClickListener(new OnShowCardClickListener(show));

                cards.add(mCard);
                mCardArrayAdapter.notifyDataSetChanged();
            }


            //Staggered grid view
            CardGridStaggeredView staggeredView = (CardGridStaggeredView) findViewById(R.id.carddemo_extras_grid_stag);

            //Set the empty view
            staggeredView.setEmptyView(findViewById(android.R.id.empty));
            if (staggeredView != null) {
                staggeredView.setAdapter(mCardArrayAdapter);
            }


        }

    }

}
