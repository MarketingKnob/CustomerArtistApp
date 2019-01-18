package com.samyotech.fabcustomer.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.ZoomOutTransformation;
import com.samyotech.fabcustomer.ui.adapter.CustomViewPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.DiscoverAdapter;
import com.samyotech.fabcustomer.ui.adapter.RvArtistCatAdapter;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArtistByCatgActivity extends AppCompatActivity  {

    private RecyclerView rvDiscover;
    private DiscoverAdapter discoverAdapter;
    private LinearLayoutManager mLayoutManager;
    private CustomTextViewBold  tvNotFound,tvNameHedar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SharedPrefrence prefrence;
    HashMap<String, String> parms = new HashMap<>();
    private UserDTO userDTO;
    private ArrayList<AllAtristListDTO> allAtristListDTOList;
    private static final String TAG = "ArtistByCatgActivity";
    private LayoutInflater myInflater;
    private LinearLayout llMain;
    private String strHeader="",StrId="";
    ViewPager mViewPager;
    CustomViewPagerAdapter custompageradpter;
    ImageView ivNext,ivPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_by_catg);
        setUiAction();

        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvNameHedar.setText(strHeader);
    }

    public void setUiAction() {

        prefrence           = SharedPrefrence.getInstance(this);
        userDTO             = prefrence.getParentUser(Consts.USER_DTO);
        myInflater          = LayoutInflater.from(this);

        parms.put(Consts.USER_ID, userDTO.getUser_id());

        Intent intent = getIntent();
        StrId       = intent.getStringExtra("CatId");
        strHeader   = intent.getStringExtra("CatName");

        Log.d(TAG, "setUiAction: "+StrId);
        if (!StrId.equals("")){
            parms.put(Consts.CATEGORY_ID, StrId);
        }

        tvNotFound          =  findViewById(R.id.tvNotFound);
        llMain              =  findViewById(R.id.ll_back_artists);
        tvNameHedar         =  findViewById(R.id.tvNameHedar);
        mViewPager          =  findViewById(R.id.images_pager);
        ivNext              =  findViewById(R.id.img_next);
        ivPrev              =  findViewById(R.id.img_previous);

//        mLayoutManager      =  new LinearLayoutManager(this.getApplicationContext());
//        rvDiscover.setLayoutManager(mLayoutManager);

        getArtist();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // cancle the Visual indication of a refresh
//                        swipeRefreshLayout.setRefreshing(false);
//                        if (NetworkManager.isConnectToInternet(ArtistByCatgActivity.this)) {
//                            swipeRefreshLayout.setRefreshing(true);
//                            parms.put(Consts.CATEGORY_ID, "");
//                            getArtist();
//
//                        } else {
//                            ProjectUtils.showToast(ArtistByCatgActivity.this, getResources().getString(R.string.internet_concation));
//                        }
//
//                    }
//                }, 3000);
//            }
//        });

       final Animation animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.click_event);

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(getItemofviewpager(-1), true);
                ivPrev.startAnimation(animBounce);

            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(getItemofviewpager(+1), true);
                ivNext.startAnimation(animBounce);

            }
        });
    }

    public void getArtist() {
        parms.put(Consts.LATITUDE, prefrence.getValue(Consts.LATITUDE));
        parms.put(Consts.LONGITUDE, prefrence.getValue(Consts.LONGITUDE));
        // ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ALL_ARTISTS_API, parms, this).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
//                swipeRefreshLayout.setRefreshing(false);
                //ProjectUtils.pauseProgressDialog();
                if (flag) {
                    try {
                        allAtristListDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<AllAtristListDTO>>() {
                        }.getType();
                        allAtristListDTOList = (ArrayList<AllAtristListDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                } else {
                    ProjectUtils.showToast(ArtistByCatgActivity.this, msg);
                    tvNotFound.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.GONE);
                    ivNext.setVisibility(View.GONE);
                    ivPrev.setVisibility(View.GONE);

                }
            }
        });
    }

    public void showData() {

//        tvNotFound.setVisibility(View.GONE);
//        rvDiscover.setVisibility(View.VISIBLE);
//        discoverAdapter = new DiscoverAdapter(this, allAtristListDTOList, myInflater);
//        rvDiscover.setAdapter(discoverAdapter);

        ZoomOutTransformation zoomOutTransformation = new ZoomOutTransformation();
        tvNotFound.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        ivNext.setVisibility(View.VISIBLE);
        ivPrev.setVisibility(View.VISIBLE);
        custompageradpter = new CustomViewPagerAdapter(this,allAtristListDTOList);
        mViewPager.setAdapter(custompageradpter);
        mViewPager.setPageTransformer(true, zoomOutTransformation);
    }


    private int getItemofviewpager(int i) {
        return mViewPager.getCurrentItem() + i;
    }

}
