package com.samyotech.fabcustomer.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.gson.Gson;
import com.samyotech.fabcustomer.DTO.ArtistBookingDTO;
import com.samyotech.fabcustomer.DTO.ArtistDetailsDTO;
import com.samyotech.fabcustomer.DTO.GalleryDTO;
import com.samyotech.fabcustomer.DTO.ProductDTO;
import com.samyotech.fabcustomer.DTO.QualificationsDTO;
import com.samyotech.fabcustomer.DTO.ReviewsDTO;
import com.samyotech.fabcustomer.DTO.SkillsDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.adapter.GalleryPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.PreviousworkPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.ProductPagerAdapter;
import com.samyotech.fabcustomer.ui.adapter.QualificationAdapter;
import com.samyotech.fabcustomer.ui.adapter.ReviewAdapter;
import com.samyotech.fabcustomer.ui.adapter.SkillsAdapter;
import com.samyotech.fabcustomer.utils.AutoScrollViewPager;
import com.samyotech.fabcustomer.utils.CustomButton;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

public class ArtistProfileView extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private String TAG = ArtistProfileView.class.getSimpleName();
    private Context mContext;
    private LinearLayout llBack;
    private CustomButton cbRequest;
    private AppCompatImageView cbChat;
    private CustomTextViewBold tvNameHedar, tvName, tvReviewsText;
    private CircleImageView ivArtist;
    private CustomTextView tvWork, tvLocation, tvArtistRate, tvRating, tvJobComplete, tvProfileComplete, tvAbout;
    private RatingBar ratingbar;
    private RecyclerView rvQualification, rvSkills, rvReviews;
    private ViewPager vpProducts, vpPreviousWork;
    private ImageView ic_left_pro, ic_right_pro, ic_left_pw, ic_right_pw, ivFav;
    private String artist_id = "";
    private ArtistDetailsDTO artistDetailsDTO;

    private SkillsAdapter skillsAdapter;
    private QualificationAdapter qualificationAdapter;
    private ProductPagerAdapter productPagerAdapter;
    private PreviousworkPagerAdapter previousworkPagerAdapter;
    private ReviewAdapter reviewAdapter;
    public GalleryPagerAdapter galleryPagerAdapter;

    private LinearLayoutManager mLayoutManagerSkills, mLayoutManagerQuali, mLayoutManagerReview;

    ArrayList<GalleryDTO> galleryList;
    private ArrayList<SkillsDTO> skillsDTOList;
    private ArrayList<QualificationsDTO> qualificationsDTOList;
    private ArrayList<ProductDTO> productDTOList;
    private ArrayList<ArtistBookingDTO> artistBookingDTOList;
    private ArrayList<ReviewsDTO> reviewsDTOList;

    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private CustomTextViewBold tvBookNow;
    private HashMap<String, String> paramsBookingOp;
    private HashMap<String, String> paramBookAppointment = new HashMap<>();
    private HashMap<String, String> paramsFav = new HashMap<>();

    private Date date;
    SimpleDateFormat sdf1, timeZone;
    public static String name = "", email = "", mobile = "";
    private DialogInterface dialog_book;
    public AutoScrollViewPager viewEvents;
    public LinearLayout viewDots;
    public int dotsCount;
    private ImageView[] dots;
    Slider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile_view);
        mContext = ArtistProfileView.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        sdf1 = new SimpleDateFormat(Consts.DATE_FORMATE_SERVER, Locale.ENGLISH);
        timeZone = new SimpleDateFormat(Consts.DATE_FORMATE_TIMEZONE, Locale.ENGLISH);

        date = new Date();
        userDTO = prefrence.getParentUser(Consts.USER_DTO);
        if (getIntent().hasExtra(Consts.ARTIST_ID)) {
            artist_id = getIntent().getStringExtra(Consts.ARTIST_ID);

        }
        parms.put(Consts.ARTIST_ID, artist_id);
        parms.put(Consts.USER_ID, userDTO.getUser_id());

        paramsFav.put(Consts.ARTIST_ID, artist_id);
        paramsFav.put(Consts.USER_ID, userDTO.getUser_id());



        setUiAction();
    }

    public void setUiAction() {
        viewEvents = (AutoScrollViewPager) findViewById(R.id.viewEvents);
        viewDots = (LinearLayout) findViewById(R.id.viewDots);

        ivFav = findViewById(R.id.ivFav);
        tvBookNow = findViewById(R.id.tvBookNow);
        llBack = findViewById(R.id.llBack);
        cbChat = findViewById(R.id.cbChat);
        cbRequest = findViewById(R.id.cbRequest);
//        tvNameHedar = findViewById(R.id.tvNameHedar);
        tvName = findViewById(R.id.tvName);
        ivArtist = findViewById(R.id.ivArtist);
        tvWork = findViewById(R.id.tvWork);
        tvLocation = findViewById(R.id.tvLocation);
        tvArtistRate = findViewById(R.id.tvArtistRate);
        tvRating = findViewById(R.id.tvRating);
        tvJobComplete = findViewById(R.id.tvJobComplete);
        tvProfileComplete = findViewById(R.id.tvProfileComplete);
        tvAbout = findViewById(R.id.tvAbout);
        tvReviewsText = findViewById(R.id.tvReviewsText);
        ratingbar = findViewById(R.id.ratingbar);
        rvQualification = findViewById(R.id.rvQualification);
        rvSkills = findViewById(R.id.rvSkills);
        rvReviews = findViewById(R.id.rvReviews);
        vpProducts = findViewById(R.id.vpProducts);
        vpPreviousWork = findViewById(R.id.vpPreviousWork);
        ic_left_pro = findViewById(R.id.ic_left_pro);
        ic_right_pro = findViewById(R.id.ic_right_pro);
        ic_left_pw = findViewById(R.id.ic_left_pw);
        ic_right_pw = findViewById(R.id.ic_right_pw);
        slider = findViewById(R.id.slider);

//        llBack.setOnClickListener(this);
        cbChat.setOnClickListener(this);
        cbRequest.setOnClickListener(this);
        ic_left_pro.setOnClickListener(this);
        ic_right_pro.setOnClickListener(this);
        ic_left_pw.setOnClickListener(this);
        ic_right_pw.setOnClickListener(this);
        tvBookNow.setOnClickListener(this);
        ivFav.setOnClickListener(this);


        mLayoutManagerSkills = new LinearLayoutManager(getApplicationContext());
        mLayoutManagerQuali = new LinearLayoutManager(getApplicationContext());
        mLayoutManagerReview = new LinearLayoutManager(getApplicationContext());


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSkills.setLayoutManager(layoutManager);
        rvSkills.setItemAnimator(new DefaultItemAnimator());

        rvQualification.setLayoutManager(mLayoutManagerQuali);
        rvReviews.setLayoutManager(mLayoutManagerReview);

        if (NetworkManager.isConnectToInternet(mContext)) {
            getArtist();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }

        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"http://cssslider.com/sliders/demo-20/data1/images/picjumbo.com_img_4635.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1,"http://cssslider.com/sliders/demo-12/data1/images/picjumbo.com_hnck1995.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2,"http://cssslider.com/sliders/demo-19/data1/images/picjumbo.com_hnck1588.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3,"http://wowslider.com/sliders/demo-18/data1/images/shanghai.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slider.addSlides(slideList);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBookNow:
                bookDailog();
                break;
            case R.id.llBack:
                finish();
                break;
            case R.id.ivFav:
                if (NetworkManager.isConnectToInternet(mContext)) {
                    if (artistDetailsDTO.getFav_status().equalsIgnoreCase("1")) {
                        removeFav();
                    } else {
                        addFav();
                    }

                } else {
                    ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                }

                break;
            case R.id.cbChat:
                Intent in = new Intent(mContext, OneTwoOneChat.class);
                in.putExtra(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
                in.putExtra(Consts.ARTIST_NAME, artistDetailsDTO.getName());
                mContext.startActivity(in);
                break;
            case R.id.cbRequest:
                paramBookAppointment.put(Consts.USER_ID, userDTO.getUser_id());
                paramBookAppointment.put(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
                clickScheduleDateTime();
                break;
            case R.id.ic_left_pro:
                int previous = getItemMinusPro(1);
                vpProducts.setCurrentItem(previous);
                break;
            case R.id.ic_right_pro:
                int next = getItemPlusPro(1);
                vpProducts.setCurrentItem(next);
                break;
            case R.id.ic_left_pw:
                int previousPw = getItemMinusPW(1);
                vpPreviousWork.setCurrentItem(previousPw);
                break;
            case R.id.ic_right_pw:
                int nextPw = getItemPlusPW(1);
                vpPreviousWork.setCurrentItem(nextPw);
                break;

        }
    }

    public void getArtist() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ARTIST_BY_ID_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    try {
                        artistDetailsDTO = new Gson().fromJson(response.getJSONObject("data").toString(), ArtistDetailsDTO.class);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public void showData() {
//        tvNameHedar.setText(artistDetailsDTO.getName());
        tvName.setText(artistDetailsDTO.getName());
        ratingbar.setRating(Float.parseFloat(artistDetailsDTO.getAva_rating()));
        tvWork.setText(artistDetailsDTO.getCategory_name());
        tvLocation.setText(artistDetailsDTO.getLocation());

//        if (artistDetailsDTO.getArtist_commission_type().equalsIgnoreCase("0")){
//
//            if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("0")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+getResources().getString(R.string.hr_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//
//            }else if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("1") && artistDetailsDTO.getFlat_type().equalsIgnoreCase("2")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+getResources().getString(R.string.hr_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//            }else if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("1") && artistDetailsDTO.getFlat_type().equalsIgnoreCase("1")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+getResources().getString(R.string.hr_add_on)+" " + artistDetailsDTO.getCategory_price()+"%");
//            }else {
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice() + getResources().getString(R.string.hr_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//            }
//
//        }else {
//            if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("0")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+" " + getResources().getString(R.string.fixed_rate_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//            }else if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("1") && artistDetailsDTO.getFlat_type().equalsIgnoreCase("2")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+" " + getResources().getString(R.string.fixed_rate_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//            }else if (artistDetailsDTO.getCommission_type().equalsIgnoreCase("1") && artistDetailsDTO.getFlat_type().equalsIgnoreCase("1")){
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+" " + getResources().getString(R.string.fixed_rate_add_on)+" " + artistDetailsDTO.getCategory_price()+"%");
//            }else {
//                tvArtistRate.setText(artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getPrice()+" " + getResources().getString(R.string.fixed_rate_add_on)+" "+artistDetailsDTO.getCurrency_type() + artistDetailsDTO.getCategory_price());
//            }
//
//        }

        tvArtistRate.setText("Response rate: 1 hrs");

        tvRating.setText("(" + artistDetailsDTO.getAva_rating() + "/5)");
        tvJobComplete.setText(artistDetailsDTO.getJobDone() + " "+getResources().getString(R.string.jobs_completed));
        tvProfileComplete.setText(artistDetailsDTO.getName());
        tvProfileComplete.setText(artistDetailsDTO.getCompletePercentages() +""+getResources().getString(R.string.completion));

        tvAbout.setText(artistDetailsDTO.getAbout_us());

        if (artistDetailsDTO.getFav_status().equalsIgnoreCase("1")) {
            ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_fav_full));
        } else {
            ivFav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_fav_blank));
        }

        Glide.with(mContext).
                load(artistDetailsDTO.getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivArtist);

        skillsDTOList = new ArrayList<>();
        skillsDTOList = artistDetailsDTO.getSkills();
        skillsAdapter = new SkillsAdapter(mContext, skillsDTOList);
        rvSkills.setAdapter(skillsAdapter);

        qualificationsDTOList = new ArrayList<>();
        qualificationsDTOList = artistDetailsDTO.getQualifications();
        qualificationAdapter = new QualificationAdapter(mContext, qualificationsDTOList);
        rvQualification.setAdapter(qualificationAdapter);


        artistBookingDTOList = new ArrayList<>();
        artistBookingDTOList = artistDetailsDTO.getArtist_booking();
        previousworkPagerAdapter = new PreviousworkPagerAdapter(mContext, artistBookingDTOList);
        vpPreviousWork.setAdapter(previousworkPagerAdapter);
        vpPreviousWork.setPageTransformer(true, new ZoomOutSlideTransformer());
        vpPreviousWork.setCurrentItem(0);
        vpPreviousWork.setOnPageChangeListener(this);

        productDTOList = new ArrayList<>();
        productDTOList = artistDetailsDTO.getProducts();
        productPagerAdapter = new ProductPagerAdapter(mContext, productDTOList);
        vpProducts.setAdapter(productPagerAdapter);
        vpProducts.setPageTransformer(true, new ZoomOutSlideTransformer());
        vpProducts.setCurrentItem(0);
        vpProducts.setOnPageChangeListener(this);

        reviewsDTOList = new ArrayList<>();
        reviewsDTOList = artistDetailsDTO.getReviews();
        reviewAdapter = new ReviewAdapter(mContext, reviewsDTOList);
        rvReviews.setAdapter(reviewAdapter);
        tvReviewsText.setText(getString(R.string.reviews) + reviewsDTOList.size() + ")");


        galleryList = new ArrayList<>();
        galleryList = artistDetailsDTO.getGallery();
        galleryPagerAdapter = new GalleryPagerAdapter(mContext, galleryList, ArtistProfileView.this);
        viewEvents.setAdapter(galleryPagerAdapter);
        viewEvents.setCurrentItem(0);
        viewEvents.setOnPageChangeListener(this);
        viewEvents.startAutoScroll();
        viewEvents.setInterval(5000);
        viewEvents.setCycle(true);
        viewEvents.setStopScrollWhenTouch(true);
        setPageViewIndicator(galleryPagerAdapter, viewEvents, viewDots);
    }
    private void setPageViewIndicator(final GalleryPagerAdapter galleryPagerAdapter, final AutoScrollViewPager viewEvents, final LinearLayout viewDots) {
        viewDots.removeAllViews();
        try {

            Log.d("###setPageViewIndicator", " : called");
            dotsCount = galleryPagerAdapter.getCount();
            dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(mContext);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        20,
                        20
                );

                params.setMargins(4, 0, 4, 0);

                final int presentPosition = i;
                dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewEvents.setCurrentItem(presentPosition);
                        return true;
                    }

                });


                viewDots.addView(dots[i], params);
            }

            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/*        int lastPosPro = productDTOList.size();
        if (position == 0) {
            ic_left_pro.setVisibility(View.GONE);
        } else {
            ic_left_pro.setVisibility(View.VISIBLE);
        }
        if (position == (lastPosPro - 1)) {

            ic_right_pro.setVisibility(View.GONE);
        } else {
            ic_right_pro.setVisibility(View.VISIBLE);
        }

        int lastPosPW = artistBookingDTOList.size();
        if (position == 0) {
            ic_left_pw.setVisibility(View.GONE);
        } else {
            ic_left_pw.setVisibility(View.VISIBLE);
        }
        if (position == (lastPosPW - 1)) {

            ic_right_pw.setVisibility(View.GONE);
        } else {
            ic_right_pw.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getItemPlusPro(int i) {
        return vpProducts.getCurrentItem() + i;
    }

    public int getItemMinusPro(int i) {
        return vpProducts.getCurrentItem() - i;
    }

    public int getItemPlusPW(int i) {
        return vpPreviousWork.getCurrentItem() + i;
    }

    public int getItemMinusPW(int i) {
        return vpPreviousWork.getCurrentItem() - i;
    }


    public void bookArtist() {

        paramsBookingOp = new HashMap<>();
        paramsBookingOp.put(Consts.USER_ID, userDTO.getUser_id());
        paramsBookingOp.put(Consts.ARTIST_ID, artistDetailsDTO.getUser_id());
        paramsBookingOp.put(Consts.DATE_STRING, sdf1.format(date).toString().toUpperCase());
        paramsBookingOp.put(Consts.TIMEZONE, timeZone.format(date));
        paramsBookingOp.put(Consts.PRICE, artistDetailsDTO.getPrice());

        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.BOOK_ARTIST_API, paramsBookingOp, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    dialog_book.dismiss();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }


    public void bookAppointment() {

        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.BOOK_APPOINTMENT_API, paramBookAppointment, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.pauseProgressDialog();
                    ProjectUtils.showToast(mContext, msg);

                } else {
                    ProjectUtils.showToast(mContext, msg);
                }


            }
        });
    }


    public void clickScheduleDateTime() {
        new SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .mustBeOnFuture()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        paramBookAppointment.put(Consts.DATE_STRING, String.valueOf(sdf1.format(date).toString().toUpperCase()));
                        paramBookAppointment.put(Consts.TIMEZONE, String.valueOf(timeZone.format(date)));
                        bookAppointment();
                    }
                })
                .display();
    }

    /*
    *  ProjectUtils.showDialog(mContext, "", "Are you sure you want to start a service with this Artist?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bookArtist();

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);

    *
    *
    * */
    public void bookDailog() {
        try {
            new AlertDialog.Builder(mContext)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(getResources().getString(R.string.book_artist))
                    .setMessage(getResources().getString(R.string.book_msg)+artistDetailsDTO.getName()+"?")
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog_book = dialog;
                            bookArtist();

                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
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


    public void addFav() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.ADD_FAVORITES_API, paramsFav, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    getArtist();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public void removeFav() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.REMOVE_FAVORITES_API, paramsFav, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    getArtist();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

}
