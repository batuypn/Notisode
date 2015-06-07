package edu.ozyegin.notisode.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.ozyegin.notisode.PaletteTransformation;
import edu.ozyegin.notisode.R;
import edu.ozyegin.notisode.listeners.OnItemClickListener;
import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 3.6.2015.
 */
public class ShowAdapter extends RecyclerView.Adapter<ShowViewHolder> {
    private Context mContext;

    private ArrayList<Show> mShows = new ArrayList<>();

    private int mScreenWidth;

    private int mDefaultTextColor;
    private int mDefaultBackgroundColor;

    private OnItemClickListener onItemClickListener;

    public ShowAdapter() {
    }

    public ShowAdapter(ArrayList<Show> shows) {
        this.mShows = shows;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<Show> shows) {
        this.mShows = shows;
        notifyDataSetChanged();
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_show, viewGroup, false);

        //set the mContext
        this.mContext = viewGroup.getContext();

        //get the colors
        mDefaultTextColor = mContext.getResources().getColor(R.color.text_without_palette);
        mDefaultBackgroundColor = mContext.getResources().getColor(R.color.image_without_palette);

        //get the screenWidth :D optimize everything :D
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        return new ShowViewHolder(rowView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(final ShowViewHolder showViewHolder, final int position) {

        final Show currentShow = mShows.get(position);
        showViewHolder.tv_name.setText(currentShow.getTitle());
        showViewHolder.tv_date.setText(Integer.toString(currentShow.getYear()));
        //showViewHolder.iv_poster.setDrawingCacheEnabled(true);
        showViewHolder.iv_poster.setImageBitmap(null);

        //reset colors so we prevent crazy flashes :D
        showViewHolder.tv_name.setTextColor(mDefaultTextColor);
        showViewHolder.tv_date.setTextColor(mDefaultTextColor);
//        showViewHolder.imageTextContainer.setBackgroundColor(mDefaultBackgroundColor);

        //cancel any loading images on this view
        Picasso.with(mContext).cancelRequest(showViewHolder.iv_poster);
        //load the image
        Picasso.with(mContext).load(mShows.get(position).getImages().getFanart().getThumb()).transform(PaletteTransformation.instance()).into(showViewHolder.iv_poster, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {

//                mShows.get(position).setSwatch(Utils.getSwatch(((BitmapDrawable)showViewHolder.iv_poster.getDrawable()).getBitmap()));

                if (Build.VERSION.SDK_INT >= 21) {
                    showViewHolder.iv_poster.setTransitionName("cover" + position);
                }
                showViewHolder.imageTextContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(v, position);
                    }
                });
            }
        });

        ViewTreeObserver vto = showViewHolder.itemView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                showViewHolder.gradientover.getLayoutParams().height = showViewHolder.itemView.getHeight();
                showViewHolder.gradientover.requestLayout();
                showViewHolder.gradientover.invalidate();
            }
        });


        //calculate height of the list-item so we don't have jumps in the view
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        //image.width .... image.height
        //device.width ... device
//        if (position % 2 == 1) {
//            showViewHolder.iv_poster.getLayoutParams().height = mShows.get(position - 1).getImage_height();
////            showViewHolder.iv_poster.getLayoutParams().height = 200;
//        }
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }
}

class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final FrameLayout imageTextContainer;
    protected final RelativeLayout parentLayout;
    protected final ImageView iv_poster;
    protected final TextView tv_name;
    protected final TextView tv_date;
    protected final View gradientover;
    private final OnItemClickListener onItemClickListener;

    public ShowViewHolder(View itemView, OnItemClickListener onItemClickListener) {

        super(itemView);
        this.onItemClickListener = onItemClickListener;

        imageTextContainer = (FrameLayout) itemView.findViewById(R.id.item_image_text_container);
        iv_poster = (ImageView) itemView.findViewById(R.id.item_image_img);
        tv_name = (TextView) itemView.findViewById(R.id.item_image_name);
        tv_date = (TextView) itemView.findViewById(R.id.item_image_date);
        gradientover = itemView.findViewById(R.id.gradientover);
        parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);

        iv_poster.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onClick(v, getPosition());
    }
}
