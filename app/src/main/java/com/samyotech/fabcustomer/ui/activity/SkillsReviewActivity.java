package com.samyotech.fabcustomer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.samyotech.fabcustomer.DTO.ArtistBookingDTO;
import com.samyotech.fabcustomer.DTO.ProductDTO;
import com.samyotech.fabcustomer.DTO.QualificationsDTO;
import com.samyotech.fabcustomer.DTO.ReviewsDTO;
import com.samyotech.fabcustomer.DTO.SkillsDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.ui.adapter.ReviewAdapter;
import com.samyotech.fabcustomer.ui.adapter.SkillsAdapter;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.InputFieldView;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import java.util.ArrayList;

public class SkillsReviewActivity extends AppCompatActivity {
    Intent intent;
    private ArrayList<SkillsDTO> skillsDTOList;
    private ArrayList<ReviewsDTO> reviewsDTOList;
    RecyclerView rvSkills,rvReviews;
    private LinearLayoutManager  mLayoutManagerReview;
    private static final String TAG = "SkillsReviewActivity";
    private SkillsAdapter skillsAdapter;
    private ReviewAdapter reviewAdapter;
    private CustomTextViewBold  tvReviewsText;
    LinearLayout llBack;
    CardView card_review,card_skills;
    CustomTextViewBold header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills_review);

        init();

    }

    void init(){


        rvSkills        = findViewById(R.id.rvSkills);
        llBack          = findViewById(R.id.llBack);
        rvReviews       = findViewById(R.id.rvReviews);
        tvReviewsText   = findViewById(R.id.tvReviewsText);
        card_review     = findViewById(R.id.card_review);
        card_skills     = findViewById(R.id.card_skills);
        header          = findViewById(R.id.header_tv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSkills.setLayoutManager(layoutManager);
        rvSkills.setItemAnimator(new DefaultItemAnimator());
        mLayoutManagerReview = new LinearLayoutManager(getApplicationContext());

        rvReviews.setLayoutManager(mLayoutManagerReview);

        if (NetworkManager.isConnectToInternet(this)) {

            intent                  = getIntent();
            Bundle bundle           = intent.getBundleExtra("BUNDLE");

            if (bundle.containsKey("SkillsArray")){
                header.setText("Skills");
                skillsDTOList           = (ArrayList<SkillsDTO>) bundle.getSerializable("SkillsArray");
                skillsAdapter           = new SkillsAdapter(this, skillsDTOList);
                rvSkills.setAdapter(skillsAdapter);
                card_review.setVisibility(View.GONE);

            }else if (bundle.containsKey("ReviewArray")){
                header.setText("Review");
                reviewsDTOList          = (ArrayList<ReviewsDTO>) bundle.getSerializable("ReviewArray");
                card_skills.setVisibility(View.GONE);
                reviewAdapter = new ReviewAdapter(this, reviewsDTOList);
                rvReviews.setAdapter(reviewAdapter);
                tvReviewsText.setText(getString(R.string.reviews) + reviewsDTOList.size() + ")");

            }

        } else {
            ProjectUtils.showToast(this, getResources().getString(R.string.internet_concation));
        }

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
