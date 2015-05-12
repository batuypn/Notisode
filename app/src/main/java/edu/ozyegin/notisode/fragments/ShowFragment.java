package edu.ozyegin.notisode.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;

import edu.ozyegin.notisode.R;
import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 11.5.2015.
 */
public class ShowFragment extends Fragment {
    DecimalFormat df = new DecimalFormat("#.00");
    private View rootView;
    private ImageView iv_poster;
    private TextView tv_header;
    private TextView tv_rating;
    private RelativeLayout lo_background;
    private Show show;
    private MaterialViewPager mViewPager;
    private ImageView imageView;
    public ShowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_show, container, false);
        mViewPager = (MaterialViewPager) rootView.findViewById(R.id.materialViewPager);

        if (getArguments() != null) {
            this.show = (Show) getArguments().getSerializable("show");
        }
        Picasso.with(getActivity()).setIndicatorsEnabled(false);
        Picasso.with(getActivity()).load(show.getImages().getFanart().getMedium()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                mViewPager.setImageDrawable(drawable, 500);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        ViewPager viewPager = mViewPager.getViewPager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab "+position;
            }

            @Override
            public Fragment getItem(int position) {
                return new ShowInfoFragment();
            }


        });
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

//        iv_poster = (ImageView) rootView.findViewById(R.id.iv_poster);
//        tv_header = (TextView) rootView.findViewById(R.id.tv_header);
//        tv_rating = (TextView) rootView.findViewById(R.id.tv_rating);
//        lo_background = (RelativeLayout) rootView.findViewById((R.id.lo_background));
//        if (getArguments() != null) {
//            this.show = (Show) getArguments().getSerializable("show");
//        }
//        Picasso.with(getActivity()).setIndicatorsEnabled(false);
//        Picasso.with(getActivity()).load(show.getImages().getClearart().getFull()).into(iv_poster,new Callback() {
//            @Override
//            public void onSuccess() {
//                tv_header.setText(show.getOverview());
//                tv_rating.setText(df.format(show.getRating())+"/10");
//                tv_header.setTextColor(Color.WHITE);
//                tv_rating.setTextColor(Color.WHITE);
//                Bitmap bitmap;
//                bitmap = ((BitmapDrawable)iv_poster.getDrawable()).getBitmap();
//                int redBucket = 0;
//                int greenBucket = 0;
//                int blueBucket = 0;
//                int pixelCount = 0;
//
//                for (int y = 0; y < bitmap.getHeight(); y++)
//                {
//                    for (int x = 0; x < bitmap.getWidth(); x++)
//                    {
//                        int c = bitmap.getPixel(x, y);
//
//                        pixelCount++;
//                        redBucket += Color.red(c);
//                        greenBucket += Color.green(c);
//                        blueBucket += Color.blue(c);
//                        // does alpha matter?
//                    }
//                }
//
//                int averageColor = Color.argb(180,redBucket / pixelCount,greenBucket / pixelCount,blueBucket / pixelCount);
//                lo_background.setBackgroundColor(averageColor);
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });


        return rootView;

    }
}
