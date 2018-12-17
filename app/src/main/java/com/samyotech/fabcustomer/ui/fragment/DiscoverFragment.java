package com.samyotech.fabcustomer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.DTO.CategoryDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.adapter.DiscoverAdapter;
import com.samyotech.fabcustomer.ui.adapter.RvArtistCatAdapter;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;
import com.samyotech.fabcustomer.utils.SpinnerDialog;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DiscoverFragment extends Fragment implements View.OnClickListener
//        , SwipeRefreshLayout.OnRefreshListener
{
    private String TAG = DiscoverFragment.class.getSimpleName();
    private View view;
    private RecyclerView rvDiscover,rvCategory;
    private DiscoverAdapter discoverAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<AllAtristListDTO> allAtristListDTOList;
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    HashMap<String, String> parms = new HashMap<>();
    private LayoutInflater myInflater;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomTextViewBold tvFilter, tvNotFound;
    private ArrayList<CategoryDTO> categoryDTOS;
    private HashMap<String, String> parmsCategory = new HashMap<>();
    private SpinnerDialog spinnerDialogCate;

    RvArtistCatAdapter rvArtistCatAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        userDTO = prefrence.getParentUser(Consts.USER_DTO);
        parms.put(Consts.USER_ID, userDTO.getUser_id());
        parmsCategory.put(Consts.USER_ID, userDTO.getUser_id());
        myInflater = LayoutInflater.from(getActivity());

        setUiAction();
        getCategory();
        return view;

    }

    public void setUiAction() {
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        tvNotFound = view.findViewById(R.id.tvNotFound);
//        tvFilter = view.findViewById(R.id.tvFilter);
//        tvFilter.setOnClickListener(this);
//
//        rvDiscover = view.findViewById(R.id.rvDiscover);
//        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        rvDiscover.setLayoutManager(mLayoutManager);

        rvCategory  = view.findViewById(R.id.rv_category);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvCategory.setLayoutManager(layoutManager);
//        rvCategory.setItemAnimator(new DefaultItemAnimator());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvCategory.setLayoutManager(manager);

//        rvArtistCatAdapter = new RvArtistCatAdapter();
//        rvCategory.setAdapter(rvArtistCatAdapter);





    }

    @Override
    public void onResume() {
        super.onResume();
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        Log.e("Runnable", "FIRST");
//                                        if (NetworkManager.isConnectToInternet(getActivity())) {
//                                            swipeRefreshLayout.setRefreshing(true);
//                                            parms.put(Consts.CATEGORY_ID, "");
//                                            getArtist();
//
//                                        } else {
//                                            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_concation));
//                                        }
//                                    }
//                                }
//        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tvFilter:
//                spinnerDialogCate.showSpinerDialog();
//            break;

        }
    }


    public void getArtist() {
        parms.put(Consts.LATITUDE, prefrence.getValue(Consts.LATITUDE));
        parms.put(Consts.LONGITUDE, prefrence.getValue(Consts.LONGITUDE));
       // ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ALL_ARTISTS_API, parms, getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
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
                    ProjectUtils.showToast(getActivity(), msg);
                    tvNotFound.setVisibility(View.VISIBLE);
                    rvDiscover.setVisibility(View.GONE);
                }
            }
        });
    }


    public void showData() {
//        tvNotFound.setVisibility(View.GONE);
//        rvDiscover.setVisibility(View.VISIBLE);
//        discoverAdapter = new DiscoverAdapter(getActivity(), allAtristListDTOList, myInflater);
//        rvDiscover.setAdapter(discoverAdapter);
    }

//    @Override
//    public void onRefresh() {
//        Log.e("ONREFREST_Firls", "FIRS");
//        parms.put(Consts.CATEGORY_ID, "");
//        tvFilter.setText(getResources().getString(R.string.all_category));
//        getArtist();
//    }

    public void getCategory() {
        new HttpsRequest(Consts.GET_ALL_CATEGORY_API, parmsCategory, getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        categoryDTOS = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<CategoryDTO>>() {
                        }.getType();
                        categoryDTOS = (ArrayList<CategoryDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);

                        Log.d(TAG, "backResponse: "+categoryDTOS.get(0).getId());

                        rvArtistCatAdapter = new RvArtistCatAdapter(getActivity(),categoryDTOS);
                        rvCategory.setAdapter(rvArtistCatAdapter);

//                        spinnerDialogCate = new SpinnerDialog((Activity) getActivity(), categoryDTOS, getResources().getString(R.string.select_category));// With 	Animation
//                        spinnerDialogCate.bindOnSpinerListener(new OnSpinerItemClick() {
//                            @Override
//                            public void onClick(String item, String id, int position) {
//
//                                Log.d(TAG, "onClick: "+id+" Pos"+position);
//                                tvFilter.setText(item);
//                                parms.put(Consts.CATEGORY_ID, id);
//                                getArtist();
//
//                            }
//                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    ProjectUtils.showToast(getActivity(), msg);
                }
            }
        });
    }

}
