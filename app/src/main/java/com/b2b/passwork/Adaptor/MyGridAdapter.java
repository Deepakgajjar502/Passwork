package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.graphics.Color;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.b2b.passwork.Model.EventDetailModel;
import com.b2b.passwork.Model.Upcoming.upcomingDataItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyGridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    LayoutInflater inflater;
    List<EventDetailModel> HisotryScheduleList;
    ArrayList<EventDetailModel> DayDetaiList =new ArrayList<>();
    Context mcontext;


    public MyGridAdapter(Context context, List<Date> dates, Calendar calendar, ArrayList<EventDetailModel> upcomingScheduleList) {
        super(context, R.layout.single_cell_layout);
        this.dates = dates;
        this.currentDate = calendar;
        this.HisotryScheduleList = upcomingScheduleList;
        inflater = LayoutInflater.from(context);
        this.mcontext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalender = Calendar.getInstance();
        dateCalender.setTime(monthDate);
        int DayNo = dateCalender.get(Calendar.DAY_OF_MONTH);

        int displayMonth = dateCalender.get(Calendar.MONTH)+1;
        int displayYear = dateCalender.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int TodayDate = currentDate.get(Calendar.DAY_OF_MONTH);
        int tDate = currentDate.get(Calendar.DATE);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
        String formattedDate = dateFormat.format(c);
        Date today = null;
        try {
           today =dateFormat.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        View view = convertView;
        if(view==null){

        view = inflater.inflate(R.layout.single_cell_layout, parent, false);

        }

        TextView DayNumber = view.findViewById(R.id.calendar_date);
        TextView EventTitle = view.findViewById(R.id.eventId);
        ImageView circle = view.findViewById(R.id.EventDetail);
        LinearLayout dateLayout = view.findViewById(R.id.calendar_dateLayout);
        DayNumber.setText(String.valueOf(DayNo));

        Calendar TodayCalender = Calendar.getInstance();
        TodayCalender.setTime(today);
        if(DayNo==TodayCalender.get(Calendar.DAY_OF_MONTH) &&  displayMonth==TodayCalender.get(Calendar.MONTH)+1 && displayYear== TodayCalender.get(Calendar.YEAR)){

            DayNumber.setTextColor(Color.parseColor("#DC143C"));
        }


        if(displayMonth == currentMonth && displayYear == currentYear){


        }else {

            DayNumber.setTextColor(Color.parseColor("#8F8E8E"));
        }


        Calendar eventCalender = Calendar.getInstance();
        for (int i=0; i< HisotryScheduleList.size(); i++){

            eventCalender.setTime(HisotryScheduleList.get(i).getEventDate());

            if(DayNo==eventCalender.get(Calendar.DAY_OF_MONTH) &&  displayMonth==eventCalender.get(Calendar.MONTH)+1 && displayYear== eventCalender.get(Calendar.YEAR)){

                EventDetailModel dayModel = new EventDetailModel();
            //    dayModel.setEventColor(eventDetaiList.get(i).getEventColor());
                dayModel.setEventDate(HisotryScheduleList.get(i).getEventDate());
                dayModel.setEventTitle(HisotryScheduleList.get(i).getEventTitle());
                dayModel.setEventType(HisotryScheduleList.get(i).getEventType());

                DayDetaiList.add(dayModel);


                EventTitle.setText((HisotryScheduleList.get(i).getEventTitle()));
              //  EventTitle.setBackgroundColor(Color.parseColor(HisotryScheduleList.get(i).getEventColor()));
                if(HisotryScheduleList.get(i).getEventType().equals("desk")){
                circle.setVisibility(View.VISIBLE);
                  circle.setBackgroundResource(R.drawable.circlegradient);

                }else if(HisotryScheduleList.get(i).getEventType().equals("meeting")) {
                    circle.setBackgroundResource(R.drawable.blue_circle);
                    circle.setVisibility(View.VISIBLE);
                }else {
                    circle.setVisibility(View.GONE);
                }

            }


        }




        return view;
    }



    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
