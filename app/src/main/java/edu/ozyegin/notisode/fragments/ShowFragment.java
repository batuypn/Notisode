package edu.ozyegin.notisode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ozyegin.notisode.R;

/**
 * Created by Batuhan on 11.5.2015.
 */
public class ShowFragment extends Fragment {
    private View rootView;
    private ImageView iv_poster;
    private TextView tv_header;

    public ShowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_show, container, false);

        iv_poster = (ImageView) rootView.findViewById(R.id.iv_poster);
        tv_header = (TextView) rootView.findViewById(R.id.tv_header);

        return rootView;
    }
}
