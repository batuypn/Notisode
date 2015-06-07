package edu.ozyegin.notisode.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.ozyegin.notisode.R;

/**
 * Created by Erdem on 12.5.2015.
 */
public class ShowSeasonsFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<Object> mContentItems = new ArrayList<>();

    public static ShowSeasonsFragment newInstance() {
        return new ShowSeasonsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_show_seasons, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        for (int i = 0; i < 100; ++i)
            mContentItems.add(new Object());

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        static final int TYPE_HEADER = 0;
        static final int TYPE_CELL = 1;
        List<Object> contents;

        public TestRecyclerViewAdapter(List<Object> contents) {
            this.contents = contents;
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return TYPE_HEADER;
                default:
                    return TYPE_CELL;
            }
        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;

            switch (viewType) {
                case TYPE_HEADER: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.show_season_item_header, parent, false);

                    return new SeasonsHeaderViewHolder(view);
                }
                case TYPE_CELL: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.show_season_item_cell, parent, false);
                    return new RecyclerView.ViewHolder(view) {
                    };
                }
            }
            return null;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case TYPE_HEADER:
                    holder = (SeasonsHeaderViewHolder) holder;
                    ((SeasonsHeaderViewHolder) holder).root.setBackgroundColor(Color.WHITE);
                    break;
                case TYPE_CELL:
                    break;
            }
        }
    }

    class SeasonsHeaderViewHolder extends RecyclerView.ViewHolder {
        protected final LinearLayout root;
        protected final ImageView poster;
        protected final TextView status, date, channel, duration;

        public SeasonsHeaderViewHolder(View itemView) {
            super(itemView);
            root = (LinearLayout) itemView.findViewById(R.id.parentView);
            poster = (ImageView) itemView.findViewById(R.id.iv_poster);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            channel = (TextView) itemView.findViewById(R.id.tv_channel);
            duration = (TextView) itemView.findViewById(R.id.tv_duration);
        }
    }
}



