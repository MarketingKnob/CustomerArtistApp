package com.samyotech.fabcustomer.ui.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.samyotech.fabcustomer.DTO.AllJobsDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.ui.decorators.MySelectorDecorator;
import com.samyotech.fabcustomer.ui.decorators.OneDayDecorator;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalenderViewActivity extends AppCompatActivity implements OnDateSelectedListener {

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView widget;
    private static final String TAG = "CalenderViewActivity";

    private ArrayList<AllJobsDTO> allJobsDTOList;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);

        widget = findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        widget.addDecorators(
                new MySelectorDecorator(this),
                oneDayDecorator
        );

        getjobs();


    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }


    public void getjobs() {
        ProjectUtils.showProgressDialog(this, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_All_JOBS_API,  this).stringGet(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();

                        allJobsDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<AllJobsDTO>>() {
                        }.getType();
                try {
                    allJobsDTOList = (ArrayList<AllJobsDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);

                    final ArrayList<CalendarDay> dates = new ArrayList<>();
                    for (int i = 0; i <allJobsDTOList.size() ; i++) {

                        String date = allJobsDTOList.get(i).getCreated_at();
                        if(date.contains(" ")){
                            date= date.substring(0, date.indexOf(" "));

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = dateFormat.parse(date);
                            Log.d(TAG, "backResponse: "+date1);


                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}
