package com.samyotech.fabcustomer.ui.adapter;

/**
 * Created by mayank on 31/10/17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.fabcustomer.DTO.UserBooking;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.ui.activity.MapActivity;
import com.samyotech.fabcustomer.ui.fragment.MyBooking;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterCustomerBooking extends RecyclerView.Adapter<AdapterCustomerBooking.MyViewHolder> {
    private String TAG = AdapterCustomerBooking.class.getSimpleName();
    MyBooking myBooking;
    private Context mContext;
    private ArrayList<UserBooking> userBookingList;
    private HashMap<String, String> paramsDecline;
    private UserDTO userDTO;
    private HashMap<String, String> paramsBookingOp;
    private DialogInterface dialog_book;
    public AdapterCustomerBooking(MyBooking myBooking, ArrayList<UserBooking> userBookingList, UserDTO userDTO) {
        this.myBooking = myBooking;
        mContext = myBooking.getActivity();
        this.userBookingList = userBookingList;
        this.userDTO = userDTO;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_customer_booking, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(mContext).
                load(userBookingList.get(position).getArtistImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivArtist);

        if (userBookingList.get(position).getBooking_type().equalsIgnoreCase("0")) {
            if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("0")) {
                holder.ivMap.setVisibility(View.GONE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.waiting_artist));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_red));
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_waiting));
            } else if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("1")) {
                holder.ivMap.setVisibility(View.GONE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.request_acc));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_yellow));
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_accept));
            } else if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("3")) {
                holder.ivMap.setVisibility(View.VISIBLE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.work_inpro));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_green));
                holder.llTime.setVisibility(View.VISIBLE);
                holder.llCancel.setVisibility(View.GONE);
                holder.llFinish.setVisibility(View.GONE);
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_inprogress));

                SimpleDateFormat sdf = new SimpleDateFormat("mm.ss");

                try {
                    Date dt = sdf.parse(userBookingList.get(position).getWorking_min());
                    sdf = new SimpleDateFormat("HH:mm:ss");
                    System.out.println(sdf.format(dt));
                    int min = dt.getHours() * 60 + dt.getMinutes();
                    int sec = dt.getSeconds();
                    holder.chronometer.setBase(SystemClock.elapsedRealtime() - (min * 60000 + sec * 1000));
                    holder.chronometer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } else if (userBookingList.get(position).getBooking_type().equalsIgnoreCase("2")) {
            if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("0")) {
                holder.ivMap.setVisibility(View.GONE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.waiting_artist));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_red));
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_waiting));
            } else if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("1")) {
                holder.ivMap.setVisibility(View.GONE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.request_acc));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_yellow));
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_accept));
            } else if (userBookingList.get(position).getBooking_flag().equalsIgnoreCase("3")) {
                holder.ivMap.setVisibility(View.VISIBLE);
                holder.tvStatus.setText(mContext.getResources().getString(R.string.work_inpro));
                holder.llStatus.setBackground(mContext.getResources().getDrawable(R.drawable.rectangle_green));
                holder.llTime.setVisibility(View.VISIBLE);
                holder.llCancel.setVisibility(View.GONE);
                holder.llFinish.setVisibility(View.VISIBLE);
                holder.ivStatus.setBackground(mContext.getResources().getDrawable(R.drawable.ic_inprogress));
                SimpleDateFormat sdf = new SimpleDateFormat("mm.ss");

                try {
                    Date dt = sdf.parse(userBookingList.get(position).getWorking_min());
                    sdf = new SimpleDateFormat("HH:mm:ss");
                    System.out.println(sdf.format(dt));
                    int min = dt.getHours() * 60 + dt.getMinutes();
                    int sec = dt.getSeconds();
                    holder.chronometer.setBase(SystemClock.elapsedRealtime() - (min * 60000 + sec * 1000));
                    holder.chronometer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }


        holder.tvWork.setText(userBookingList.get(position).getCategory_name());
        holder.tvLocation.setText(userBookingList.get(position).getArtistLocation());
        holder.tvJobComplete.setText(userBookingList.get(position).getJobDone() + " "+mContext.getResources().getString(R.string.jobs_completed));
        holder.tvProfileComplete.setText(userBookingList.get(position).getCompletePercentages() + mContext.getResources().getString(R.string.completion));

        holder.tvName.setText(mContext.getResources().getString(R.string.booking_with)+" "+userBookingList.get(position).getArtistName());


        holder.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeDialog(mContext.getResources().getString(R.string.cancel), mContext.getResources().getString(R.string.booking_cancel_msg)+" "+userBookingList.get(position).getArtistName()+"?", position);
            }
        });
        holder.ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, MapActivity.class);
                in.putExtra(Consts.ARTIST_ID, userBookingList.get(position).getArtist_id());
                mContext.startActivity(in);
            }
        });
        holder.llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkManager.isConnectToInternet(mContext)) {
                    booking("3", position);
                } else {
                    ProjectUtils.showToast(mContext, mContext.getResources().getString(R.string.internet_concation));
                }
            }
        });
    }


    @Override
    public int getItemCount() {

        return userBookingList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivArtist;
        public LinearLayout llStatus, llCancel,llFinish;
        public CustomTextView tvStatus, tvWork, tvLocation, tvJobComplete, tvProfileComplete;
        public CustomTextViewBold tvName;
        public RelativeLayout rlClick, llTime ;
        public Chronometer chronometer;
        public ImageView ivMap,ivStatus;

        public MyViewHolder(View view) {
            super(view);
            ivArtist = view.findViewById(R.id.ivArtist);
            tvStatus = view.findViewById(R.id.tvStatus);
            llStatus = view.findViewById(R.id.llStatus);
            llCancel = view.findViewById(R.id.llCancel);
            llTime = view.findViewById(R.id.llTime);
            chronometer = view.findViewById(R.id.chronometer);
            tvWork = view.findViewById(R.id.tvWork);
            tvName = view.findViewById(R.id.tvName);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvJobComplete = view.findViewById(R.id.tvJobComplete);
            tvProfileComplete = view.findViewById(R.id.tvProfileComplete);
            ivMap = view.findViewById(R.id.ivMap);
            llFinish = view.findViewById(R.id.llFinish);
            ivStatus = view.findViewById(R.id.ivStatus);

        }
    }


    public void decline(int pos) {
        paramsDecline = new HashMap<>();
        paramsDecline.put(Consts.USER_ID, userDTO.getUser_id());
        paramsDecline.put(Consts.BOOKING_ID, userBookingList.get(pos).getId());
        paramsDecline.put(Consts.DECLINE_BY, "2");
        paramsDecline.put(Consts.DECLINE_REASON, "Busy");
        ProjectUtils.showProgressDialog(mContext, true, mContext.getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.DECLINE_BOOKING_API, paramsDecline, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                dialog_book.dismiss();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    myBooking.getBooking();


                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }
    public void booking(String req, int pos) {
        paramsBookingOp = new HashMap<>();
        paramsBookingOp.put(Consts.BOOKING_ID, userBookingList.get(pos).getId());
        paramsBookingOp.put(Consts.REQUEST, req);
        paramsBookingOp.put(Consts.USER_ID, userBookingList.get(pos).getUser_id());
        ProjectUtils.showProgressDialog(mContext, true, mContext.getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.BOOKING_OPERATION_API, paramsBookingOp, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    myBooking.getBooking();


                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }
    public void completeDialog(String title, String msg, final int pos) {
        try {
            new AlertDialog.Builder(mContext)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog_book = dialog;
                            decline(pos);

                        }
                    })
                    .setNegativeButton(mContext.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    })
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}