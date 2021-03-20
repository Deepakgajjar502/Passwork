package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b2b.passwork.Model.EventDetailModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class weeklyCalenderAdaptor extends ArrayAdapter  {

    List<Date> dates;
    Calendar currentDate;
    LayoutInflater inflater;

    ArrayList<EventDetailModel> DayDetaiList =new ArrayList<>();
    Context mcontext;
    private OnItemClickListener onItemClickListener;

    public weeklyCalenderAdaptor(Context context, List<Date> dates, Calendar calendar) {
        super(context, R.layout.single_calender);
        this.dates = dates;
        this.currentDate = calendar;
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

            view = inflater.inflate(R.layout.single_calender, parent, false);

        }

        TextView DayNumber = view.findViewById(R.id.calendar_date);

        LinearLayout dateLayout = view.findViewById(R.id.calendar_dateLayout);
        DayNumber.setText(String.valueOf(DayNo));

        Calendar TodayCalender = Calendar.getInstance();
        TodayCalender.setTime(today);
        if(DayNo==TodayCalender.get(Calendar.DAY_OF_MONTH) &&  displayMonth==TodayCalender.get(Calendar.MONTH)+1 && displayYear== TodayCalender.get(Calendar.YEAR)){

            DayNumber.setTextColor(Color.parseColor("#DC143C"));
        }


        if(displayMonth == currentMonth && displayYear == currentYear){
            DayNumber.setVisibility(View.VISIBLE);

        }else {
        DayNumber.setVisibility(View.INVISIBLE);
         //   DayNumber.setTextColor(Color.parseColor("#E4E4E4"));
        }







        return view;
    }



    @Override
    public int getCount() {

        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        notifyDataSetChanged();
        return dates.indexOf(item);

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        notifyDataSetChanged();
        return dates.get(position);

    }




}