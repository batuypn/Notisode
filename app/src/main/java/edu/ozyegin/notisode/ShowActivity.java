package edu.ozyegin.notisode;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.github.florent37.materialviewpager.MaterialViewPager;

import edu.ozyegin.notisode.fragments.PopularShowsFragment;
import edu.ozyegin.notisode.fragments.ShowInfoFragment;
import edu.ozyegin.notisode.fragments.ShowSeasonsFragment;
import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 11.5.2015.
 */
public class ShowActivity extends AppCompatActivity {
    private Show show;
    private MaterialViewPager mViewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private View header_logo;
    private TextView tv_header;
    private Palette.Swatch swatch;
    private int position;

    public ShowActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);

        if (getIntent() != null) {
            this.show = (Show) getIntent().getSerializableExtra("show");
            position = getIntent().getIntExtra("position", 0);
        }


        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        header_logo = mViewPager.getChildAt(0);
        pagerSlidingTabStrip = mViewPager.getPagerTitleStrip();

//        mViewPager.setBackgroundColor(Color.WHITE);

        initToolbar();
        initHeaderLogo();
        initListeners();

        pagerSlidingTabStrip.setAllCaps(false);
//        MaterialViewPagerImageHeader headerBackgroundImage = (MaterialViewPagerImageHeader)mViewPager.findViewById(com.github.florent37.materialviewpager.R.id.materialviewpager_imageHeader);

//        Toast.makeText(this, Integer.toString(mViewPager.getChildCount()), Toast.LENGTH_SHORT).show();

        Bitmap imageCoverBitmap = PopularShowsFragment.photoCache.get(position);
        if (imageCoverBitmap != null && !imageCoverBitmap.isRecycled()) {
            Drawable drawable = new BitmapDrawable(getResources(), imageCoverBitmap);
            mViewPager.setImageDrawable(drawable, 500);
            swatch = Utils.getSwatch(imageCoverBitmap);
            if (swatch != null) {
                mViewPager.setColor(swatch.getRgb(), 500);
                pagerSlidingTabStrip.setTextColor(swatch.getTitleTextColor());
                pagerSlidingTabStrip.setIndicatorColor(swatch.getTitleTextColor());
                tv_header.setTextColor(swatch.getTitleTextColor());
            }
        }

//        Picasso.with(this).setIndicatorsEnabled(false);
//        Picasso.with(this).load(show.getImages().getThumb().getFull()).transform(PaletteTransformation.instance()).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                mViewPager.setImageDrawable(drawable, 500);
//                swatch = Utils.getSwatch(bitmap);
//                if(swatch != null) {
//                    mViewPager.setColor(swatch.getRgb(), 500);
//                    pagerSlidingTabStrip.setTextColor(swatch.getTitleTextColor());
//                    pagerSlidingTabStrip.setIndicatorColor(swatch.getTitleTextColor());
//                    tv_header.setTextColor(swatch.getTitleTextColor());
//                }
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                mViewPager.setImageDrawable(errorDrawable, 500);
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });

        ViewPager viewPager = mViewPager.getViewPager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Info";
                    case 1:
                        return "Seasons";
                }
                return "";
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("show", show);
                        ShowInfoFragment showInfoFragment = ShowInfoFragment.newInstance();
                        showInfoFragment.setArguments(bundle);

                        return showInfoFragment;
                    case 1:
                        return ShowSeasonsFragment.newInstance();
                }
                return null;
            }

        });
        pagerSlidingTabStrip.setViewPager(viewPager);


    }


    private void initToolbar() {
        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("");
        }
    }

    private void initHeaderLogo() {
        tv_header = (TextView) header_logo.findViewById(R.id.tv_name);
        tv_header.setText(show.getTitle());
    }

    private void initListeners() {
        mViewPager.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
