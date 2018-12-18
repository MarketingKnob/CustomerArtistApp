package com.samyotech.fabcustomer.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samyotech.fabcustomer.DTO.CategoryDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.ui.activity.ArtistByCatgActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class RvArtistCatAdapter extends RecyclerView.Adapter<RvArtistCatAdapter.MyViewHolder> {

    private Activity context;
    private ArrayList<CategoryDTO> categoryDTOS;
    HashMap<String, String> parms = new HashMap<>();

    public RvArtistCatAdapter(Activity context, ArrayList<CategoryDTO> categoryDTOS) {
        this.context = context;
        this.categoryDTOS = categoryDTOS;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView ivIcon;
        private AppCompatTextView tvTitle;

        public MyViewHolder(View view) {
            super(view);

            ivIcon              = view.findViewById(R.id.iv_icon);
            tvTitle             = view.findViewById(R.id.tv_title);
        }
    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_category_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new  MyViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {

        holder.tvTitle.setText(categoryDTOS.get(position).getCat_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, "ID"+categoryDTOS.get(position).getId(), Toast.LENGTH_SHORT).show();

                final Intent intent;
                intent =  new Intent(context, ArtistByCatgActivity.class);
                intent.putExtra("CatId",categoryDTOS.get(position).getId());
                intent.putExtra("CatName",categoryDTOS.get(position).getCat_name());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryDTOS.size();
    }
}

