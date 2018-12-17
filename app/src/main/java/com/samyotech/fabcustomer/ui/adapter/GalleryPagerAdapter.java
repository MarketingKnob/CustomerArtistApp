package com.samyotech.fabcustomer.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.samyotech.fabcustomer.DTO.GalleryDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.ui.activity.ArtistProfileView;

import java.util.ArrayList;


public class GalleryPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<GalleryDTO> gallery;
    ArtistProfileView artistProfileView;

    public GalleryPagerAdapter(Context context, ArrayList<GalleryDTO> gallery, ArtistProfileView artistProfileView) {
        this.mContext = context;
        this.gallery = gallery;
        this.artistProfileView = artistProfileView;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.pager_gallary, container, false);
        ImageView iv_bottom_foster = (ImageView) itemView.findViewById(R.id.iv_bottom_foster);

        Glide
                .with(mContext)
                .load(gallery.get(position).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .into(iv_bottom_foster);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return gallery.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}