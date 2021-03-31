package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Adaptor.weeklyAdaptor;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.dateModel;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDesk extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    @BindView(R.id.backButton)
    ImageView backButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.workspaceDetail)
    LinearLayout workspaceDetail;
    UserSessionManager session;

    @BindView(R.id.selectDate)
    TextView selectDate;
    String StartDate = "", EndDate = "";
    @BindView(R.id.floorDetail)
    RecyclerView floorDetail;
    String WorkspaceId, Token, workspaceName;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    floor_adaptor adaptor;
    List<FloorsItem> floors;
    @BindView(R.id.calendar_prev_button)
    ImageView calendarPrevButton;
    @BindView(R.id.calendar_date_display)
    TextView calendarDateDisplay;
    @BindView(R.id.calendar_next_button)
    ImageView calendarNextButton;
    @BindView(R.id.monthTitle)
    RelativeLayout monthTitle;
    @BindView(R.id.calendar_header)
    LinearLayout calendarHeader;
    @BindView(R.id.calendar_grid)
    RecyclerView calendarGrid;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    List<Date> dates = new ArrayList<>();
    List<dateModel> dateList = new ArrayList<>();

    private static final int MAX_CALENDAR_DAY = 42;
    @BindView(R.id.single_date_calender)
    TextView singleDateCalender;
    @BindView(R.id.multiple_date_Calender)
    TextView multipleDateCalender;
    //   weeklyCalenderAdaptor myGridAdapter;
    weeklyAdaptor myGridAdapter;
    boolean singleDate = true;
    @BindView(R.id.confirm_button)
    Button confirmButton;
    int lastCheckedPos = -1;
    @BindView(R.id.bookforOther)
    SwitchMaterial bookforOther;
    Boolean multipleSelection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_book_desk);
        ButterKnife.bind(this);

        backButton.setOnClickListener(this);
        multipleDateCalender.setOnClickListener(this);
        singleDateCalender.setOnClickListener(this);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);

        bookforOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {

                    multipleSelection = true;
                    SetUpCalender();
                } else {

                    multipleSelection = false;
                    SetUpCalender();
                }
            }
        });


        SetUpCalender();
        calendarPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.add(Calendar.MONTH, -1);
                SetUpCalender();
            }
        });

        calendarNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalender();
            }
        });
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        EndDate = dateFormat.format(tomorrow);
        StartDate = dateFormat.format(tomorrow);

        selectDate.setText("Selected Date is : " + EndDate);

        //    getFloorDetail(WorkspaceId);


    }

    private void SetUpCalender() {

        Date d = new Date();
        CharSequence s = android.text.format.DateFormat.format("yyyy-MM-dd", d.getTime());
        Log.e("todayDate", s.toString());


        String currentDate = dateFormat.format(calendar.getTime());
        calendarDateDisplay.setText(currentDate);
        dates.clear();
        dateList.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);


        while (dates.size() < MAX_CALENDAR_DAY) {

            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);


        }

        for (Date object : dates) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(object);
            dateModel model = new dateModel(false, formattedDate);
            dateList.add(model);
            //do something with object
        }


        myGridAdapter = new weeklyAdaptor(this, dates, calendar, singleDate, dateList, confirmButton, lastCheckedPos, multipleSelection);
        calendarGrid.setLayoutManager(new GridLayoutManager(BookDesk.this, 7));
        //  myGridAdapter.setOnItemClickListener(BookDesk.this);
        calendarGrid.setAdapter(myGridAdapter);


    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.backButton:
                Log.e("click", "working");

                Intent intent = new Intent(BookDesk.this, MainActivity.class);
                startActivity(intent);

                break;


            case R.id.single_date_calender:

                singleDateCalender.setBackground(getResources().getDrawable(
                        R.drawable.selected_calender_type));
                multipleDateCalender.setBackground(null);
                singleDateCalender.setTextColor(ContextCompat.getColor(this, R.color.white));
                multipleDateCalender.setTextColor(ContextCompat.getColor(this, R.color.black));
                singleDate = true;
                lastCheckedPos = -2;
                myGridAdapter = new weeklyAdaptor(this, dates, calendar, true, dateList, confirmButton, lastCheckedPos, multipleSelection);
                calendarGrid.setAdapter(myGridAdapter);

                //do your stuff
                break;

            case R.id.multiple_date_Calender:

                multipleDateCalender.setBackground(getResources().getDrawable(
                        R.drawable.selected_calender_type));
                singleDateCalender.setBackground(null);
                singleDateCalender.setTextColor(ContextCompat.getColor(this, R.color.black));
                multipleDateCalender.setTextColor(ContextCompat.getColor(this, R.color.white));
                singleDate = false;
                lastCheckedPos = -1;
                myGridAdapter = new weeklyAdaptor(this, dates, calendar, false, dateList, confirmButton, lastCheckedPos, multipleSelection);
                calendarGrid.setAdapter(myGridAdapter);
                //do your stuff
                break;

        }


    }

    @Override
    public void onItemClick(View v, int position) {



       /* if(v.getId()==R.id.single_calendar_date)
        {
            Log.e("click", "multiple");
            *//*if(!singleDate){
                Log.e("click", "multiple");
            }*//*



        }*/


    }


}


