package com.b2b.passwork.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.AvaialbleTimeSlot_Adaptor;
import com.b2b.passwork.Adaptor.DurationTimeAdaptor;
import com.b2b.passwork.Adaptor.meetingCalenderAdaptor;
import com.b2b.passwork.Adaptor.weeklyAdaptor;
import com.b2b.passwork.Model.TimeDurationModel;
import com.b2b.passwork.Model.TimeslotModel;
import com.b2b.passwork.Model.dateModel;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingSelectDate extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    List<TimeslotModel> your_array_list = new ArrayList<TimeslotModel>();

    List<TimeDurationModel> durationList = new ArrayList<TimeDurationModel>();

    String StartTime, durationTime, selectedDate, EndTIme;
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.TimeSlot)
    RecyclerView TimeSlot;
    @BindView(R.id.TimeDuration)
    RecyclerView TimeDuration;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.confirm_button)
    Button confirmButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    AvaialbleTimeSlot_Adaptor madapter;
    DurationTimeAdaptor durationAdator;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    List<Date> dates = new ArrayList<>();
    List<dateModel> dateList = new ArrayList<>();
    private static final int MAX_CALENDAR_DAY = 42;
    meetingCalenderAdaptor myGridAdapter;
    boolean singleDate = true;
    Boolean multipleSelection = false;
    int lastCheckedPos = -1;
    String RoomName, RoomDisc, BayId, maxSeat, floorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_meeting_select_date);
        ButterKnife.bind(this);


        confirmButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        RoomName = getIntent().getStringExtra("roomName");
        RoomDisc = getIntent().getStringExtra("roomDis");
        BayId = getIntent().getStringExtra("BayId");
        maxSeat = getIntent().getStringExtra("capacity");
        floorName = getIntent().getStringExtra("floorName");


        TimeslotModel model = new TimeslotModel("10:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("11:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("12:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("01:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("02:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("03:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("04:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("05:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("06:00", "PM", false);
        your_array_list.add(model);



        TimeDurationModel   durationModel = new TimeDurationModel("01:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("02:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("03:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("04:00", "HR", false);
        durationList.add(durationModel);
        durationModel = new TimeDurationModel("05:00", "HR", false);
        durationList.add(durationModel);
        durationModel = new TimeDurationModel("06:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("07:00", "HR", false);
        durationList.add(durationModel);


        madapter = new AvaialbleTimeSlot_Adaptor(your_array_list, this, lastCheckedPos);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TimeSlot.setLayoutManager(horizontaLayoutManagaer);
        madapter.setOnItemClickListener(this);
        TimeSlot.setAdapter(madapter);

        durationAdator = new DurationTimeAdaptor(durationList, this, lastCheckedPos);
        LinearLayoutManager horizontaLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TimeDuration.setLayoutManager(horizontaLayoutManagaer1);
        durationAdator.setOnItemClickListener(this);
        TimeDuration.setAdapter(durationAdator);

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


        myGridAdapter = new meetingCalenderAdaptor(this, dates, calendar, singleDate, dateList, confirmButton, lastCheckedPos, multipleSelection);
        calendarGrid.setLayoutManager(new GridLayoutManager(MeetingSelectDate.this, 7));
        //  myGridAdapter.setOnItemClickListener(BookDesk.this);
        calendarGrid.setAdapter(myGridAdapter);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backButton:
                Log.e("click", "working");

                Intent intent = new Intent(MeetingSelectDate.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.confirm_button:


              Date selectedDate =  myGridAdapter.getSelect();
              if(selectedDate!=null) {
                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                  String formattedDate = dateFormat.format(selectedDate);
                  Log.e("selectedDate", formattedDate);

                  TimeslotModel selectTime = madapter.getSelect();
                  if(selectTime!= null){

                      Log.e("selectedtime", selectTime.getTimeSlot());
                      TimeDurationModel selectDuration = durationAdator.getSelect();
                      if(selectDuration!= null){

                          Intent intend = new Intent(MeetingSelectDate.this, WorkspaceDetail.class);
                          intend.putExtra("roomName", RoomName);
                          intend.putExtra("timeType", selectTime.getAM_PM());
                          intend.putExtra("roomDis", RoomDisc);
                          intend.putExtra("BayId", BayId);
                          intend.putExtra("floorName", floorName);
                          intend.putExtra("capacity", maxSeat);
                          intend.putExtra("startTime", selectTime.getTimeSlot());
                          intend.putExtra("EndTime",selectDuration.getTimeDuration());
                          intend.putExtra("date", formattedDate);
                          startActivity(intend);

                          Log.e("selectedDuration", selectDuration.getTimeDuration());

                      }else {
                          StaticUtil.showIOSLikeDialog(MeetingSelectDate.this, "Please select Duration");
                      }

                  }else {
                      StaticUtil.showIOSLikeDialog(MeetingSelectDate.this, "Please select Time");
                  }


              }else {
                  StaticUtil.showIOSLikeDialog(MeetingSelectDate.this, "Please select date");
              }



            /*    Intent intent1 = new Intent(MeetingSelectDate.this, MainActivity.class);
                startActivity(intent1);*/

                break;
        }


        }

    @Override
    public void onItemClick(View v, int position) {

    }
}