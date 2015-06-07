package edu.ozyegin.notisode.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import edu.ozyegin.notisode.PaletteTransformation;
import edu.ozyegin.notisode.R;
import edu.ozyegin.notisode.Utils;
import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 6.6.2015.
 */
public class ShowInfoFragment extends Fragment {
    private View rootView;
    private Bundle bundle;
    private Show show;
    private Palette.Swatch swatch;

    public static ShowInfoFragment newInstance() {
        return new ShowInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_show_info, container, false);

        if (bundle != null) {
            show = (Show) bundle.getSerializable("show");
        }

        initHeaderCardView((CardView) rootView.findViewById(R.id.header_cardView));


        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObservableScrollView mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }

    private void initHeaderCardView(CardView cardView) {
        final LinearLayout root = (LinearLayout) cardView.findViewById(R.id.header_root);
        final ImageView poster = (ImageView) cardView.findViewById(R.id.header_poster);
        final TextView status = (TextView) cardView.findViewById(R.id.header_status);
        final TextView date = (TextView) cardView.findViewById(R.id.header_date);
        final TextView channel = (TextView) cardView.findViewById(R.id.header_channel);
        final TextView duration = (TextView) cardView.findViewById(R.id.header_duration);

//
        Picasso.with(getActivity())
                .load(show.getImages().getPoster().getThumb())
                .transform(PaletteTransformation.instance())
                .resizeDimen(R.dimen.show_info_header_poster_width, R.dimen.show_info_header_poster_height)
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        swatch = Utils.getSwatch(((BitmapDrawable) poster.getDrawable()).getBitmap());
//                        root.setBackgroundColor(swatch.getRgb());
//                        status.setTextColor(swatch.getTitleTextColor());
//                        date.setTextColor(swatch.getBodyTextColor());
//                        channel.setTextColor(swatch.getBodyTextColor());
//                        duration.setTextColor(swatch.getBodyTextColor());

                        initButtonsCardView((CardView) rootView.findViewById(R.id.buttons_cardView));
                        initSummaryCardView((CardView) rootView.findViewById(R.id.summary_cardView));
                    }

                    @Override
                    public void onError() {

                    }
                });
        status.setText(show.getStatus());
        if (show.getAirs().day != null)
            date.setText(show.getAirs().day + ", " + show.getAirs().time);
        else
            date.setVisibility(View.GONE);
        channel.setText(show.getNetwork());
        duration.setText(Integer.toString(show.getRuntime()) + " mins");
    }

    private void initButtonsCardView(CardView cardView) {
        final LinearLayout root = (LinearLayout) cardView.findViewById(R.id.buttons_root);
        final ImageButton fav = (ImageButton) cardView.findViewById(R.id.buttons_fav);
        final ImageButton share = (ImageButton) cardView.findViewById(R.id.buttons_share);

        //root.setBackgroundColor(swatch.getRgb());
    }

    private void initSummaryCardView(CardView cardView) {
        final LinearLayout root = (LinearLayout) cardView.findViewById(R.id.summary_root);
        final TextView text = (TextView) cardView.findViewById(R.id.summary_text);

        //root.setBackgroundColor(swatch.getRgb());
        //text.setTextColor(swatch.getBodyTextColor());
        text.setText(show.getOverview());
    }
}
