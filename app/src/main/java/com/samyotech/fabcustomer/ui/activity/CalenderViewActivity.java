package com.samyotech.fabcustomer.ui.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.samyotech.fabcustomer.DTO.AllJobsDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.ui.adapter.RvDateNewAdapter;
import com.samyotech.fabcustomer.ui.decorators.EventDecoratorColor;
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
import java.util.HashMap;
import java.util.List;

public class CalenderViewActivity extends AppCompatActivity implements OnDateSelectedListener,OnMonthChangedListener {

    MaterialCalendarView widget;
    private static final String TAG = "CalenderViewActivity";
    Date currentDAte;
    private ArrayList<AllJobsDTO> allJobsDTOList;
    RecyclerView recyclerViewDate;
    RvDateNewAdapter rvDateNewAdapter;

    ArrayList<HashMap<String, String>> arrayListNew = new ArrayList<HashMap<String,String>>();
    ArrayList<HashMap<String, String>> arrayListMatchDate = new ArrayList<HashMap<String,String>>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);


        recyclerViewDate =findViewById(R.id.rv_jobs);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDate.setLayoutManager(layoutManager1);
        recyclerViewDate.setItemAnimator(new DefaultItemAnimator());

        Calendar c      = Calendar.getInstance();
        currentDAte     = c.getTime();

        widget          = findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.addDecorators(
                new MySelectorDecorator(this, CalendarDay.from(currentDAte))
        );
        widget.setSelectedDate(currentDAte);
        widget.setOnMonthChangedListener(this);

        getjobs();

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        arrayListMatchDate.clear();

        String strMonth= "";
        int month= date.getMonth()+1;
        if (month<10){
            strMonth =0+String.valueOf(month);
        }else {
            strMonth=String.valueOf(month);
        }
        String selected_date=date.getYear()+"-"+strMonth +"-"+date.getDay();

        Log.d(TAG, "onDateSelected: "+selected_date);

        for (int i = 0; i <arrayListNew.size() ; i++) {

            String jobDate= arrayListNew.get(i).get("Date");

            if (jobDate.equalsIgnoreCase(selected_date)) {

                Log.d(TAG, "onDateSelected:Equal "+jobDate);
                HashMap<String, String> h2 = new HashMap<String, String>();
                h2.put("EDate", jobDate);
                h2.put("EJobId", arrayListNew.get(i).get("JobId"));
                h2.put("Eusername", arrayListNew.get(i).get("username"));
                arrayListMatchDate.add(h2);

                Log.d(TAG, "onDateSelected:AfterMatch "+arrayListMatchDate.size());

            }
        }
        rvDateNewAdapter = new RvDateNewAdapter(this, arrayListMatchDate);
        recyclerViewDate.setAdapter(rvDateNewAdapter);

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

                        String strDate = allJobsDTOList.get(i).getCreated_at();
                        String strCatName = allJobsDTOList.get(i).getCategory_name();
                        String strJobId = allJobsDTOList.get(i).getJob_id();
                        if(strDate.contains(" ")){
                            strDate= strDate.substring(0, strDate.indexOf(" "));

                        }
                        HashMap<String, String> h1 = new HashMap<String, String>();
                        h1.put("Date", strDate);
                        h1.put("JobId", strJobId);
                        h1.put("username", strCatName);
                        arrayListNew.add(h1);


                        Date eventSendDate = convertStringToDate(strDate);
                        EventDecoratorColor eventDecorator = new EventDecoratorColor(widget, eventSendDate, 1, "1");
                        widget.addDecorator(eventDecorator);

                        Log.d(TAG, "backResponse: "+eventSendDate);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    Date convertStringToDate(String dtStart) {
        Date date = null;
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(dtStart);
            System.out.println(date);
            date = date1;
            Log.e("date", String.valueOf(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Log.e("date", String.valueOf(e));
            e.printStackTrace();

        }
        return date;
    }


    @Override
    public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
        Log.d(TAG, "onMonthChanged: ");

        arrayListMatchDate = new ArrayList<HashMap<String, String>>();
        rvDateNewAdapter = new RvDateNewAdapter(this, arrayListMatchDate);
        recyclerViewDate.setAdapter(rvDateNewAdapter);

    }
}
