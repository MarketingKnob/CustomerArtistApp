package com.samyotech.fabcustomer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.ui.activity.ArtistProfileView;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<AllAtristListDTO> allAtristListDTOList;

    public CustomViewPagerAdapter(Context context,ArrayList<AllAtristListDTO> IallAtristListDTOList) {
        mContext            = context;
        allAtristListDTOList= IallAtristListDTOList;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allAtristListDTOList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((LinearLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        AppCompatTextView btn_price         = view.findViewById(R.id.btn_price);
        AppCompatButton btn_set_profile     = view.findViewById(R.id.btn_set_profile);
        CircleImageView IVartist            = view.findViewById(R.id.IVartist);
        CustomTextView tvRating             = view.findViewById(R.id.tvRating);
        RatingBar ratingbar                 = view.findViewById(R.id.ratingbar);
        CustomTextViewBold CTVartistname    = view.findViewById(R.id.CTVartistname);
        CustomTextView CTVartistwork        = view.findViewById(R.id.CTVartistwork);

        CTVartistwork.setText("- "+allAtristListDTOList.get(position).getCategory_name()+"- ");
        Glide.with(mContext).
                load(allAtristListDTOList.get(position).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(IVartist);

        tvRating.setText("(" + allAtristListDTOList.get(position).getAva_rating() + "/5)");
        CTVartistname.setText(allAtristListDTOList.get(position).getName());
        ratingbar.setRating(Float.parseFloat(allAtristListDTOList.get(position).getAva_rating()));
        btn_price.setText("Rs. "+allAtristListDTOList.get(position).getPrice());
        btn_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ArtistProfileView.class);
                in.putExtra(Consts.ARTIST_ID, allAtristListDTOList.get(position).getUser_id());
                mContext.startActivity(in);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }

}