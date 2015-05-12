package edu.ozyegin.notisode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import edu.ozyegin.notisode.R;

/**
 * Created by Erdem on 12.5.2015.
 */
public class ShowInfoFragment extends Fragment{
    private View rootView;
    private RecyclerView recyclerView;
    private RecyclerViewMaterialAdapter adapter;

    private String[] mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_show_info, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);

        return rootView;
    }


}
