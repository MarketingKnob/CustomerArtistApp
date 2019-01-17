package com.samyotech.fabcustomer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samyotech.fabcustomer.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RvDateNewAdapter extends RecyclerView.Adapter<RvDateNewAdapter.MyViewHolder> {

    private Context context;
    ArrayList<HashMap<String, String>> arrayListMatchDate = new ArrayList<HashMap<String,String>>();


    public RvDateNewAdapter(Context context, ArrayList<HashMap<String, String>> arrayListMatchDate) {
        this.context = context;
        this.arrayListMatchDate = arrayListMatchDate;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvJobs,tvService,tvDate;

        public MyViewHolder(View view) {
            super(view);

            tvJobs     = view.findViewById(R.id.tv_job_id);
            tvService  = view.findViewById(R.id.tv_service);
            tvDate     = view.findViewById(R.id.tv_date);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_by_date, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        return new MyViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        int pos=position+1;

        holder.tvJobs.setText(pos+":  Job Date: "+arrayListMatchDate.get(position).get("EDate"));
        holder.tvService.setText("Job Id: "+arrayListMatchDate.get(position).get("EJobId"));
        holder.tvDate.setText("Service Type: "+arrayListMatchDate.get(position).get("Eusername"));

    }

    @Override
    public int getItemCount() {
        return arrayListMatchDate.size();
    }
}

