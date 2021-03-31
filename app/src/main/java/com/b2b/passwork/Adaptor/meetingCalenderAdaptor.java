package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.dateModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class meetingCalenderAdaptor extends RecyclerView.Adapter {

    List<Date> dates;
    Calendar currentDate;
    LayoutInflater inflater;
    List<dateModel> dateList = new ArrayList<>();
    List<String> selectedDate = new ArrayList<>();
    List<String> showselectedDate = new ArrayList<>();
    List<String> baySelectedDate = new ArrayList<>();
    Context mcontext;
    private OnItemClickListener onItemClickListener;
    private static int lastCheckedPos = -1;
    Boolean selectType;
    Button btnConfirm;
    String finalString = "0";
    String showSelectDate = "0";
    String baySelectDate = "0";
    String SingleDate = "0";
    String Multipledate = "0";
    int todayMonth;
    Boolean multipleSelection;
    Calendar TodayCalender;
    public meetingCalenderAdaptor(Context context, List<Date> dates, Calendar calendar, Boolean selectionType, List<dateModel> dateList, Button confirmButton, int lastCheckedPos, Boolean multipleSelection) {
        this.dates = dates;
        this.currentDate = calendar;
        inflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.selectType = selectionType;
        this.dateList = dateList;
        this.btnConfirm = confirmButton;
        this.lastCheckedPos = lastCheckedPos;
        this.multipleSelection = multipleSelection;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_calender, viewGroup, false);
        return new meetingCalenderAdaptor.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


            Date monthDate = dates.get(position);
            Calendar dateCalender = Calendar.getInstance();
            dateCalender.setTime(monthDate);
            int DayNo= dateCalender.get(Calendar.DAY_OF_MONTH);
            int  displayMonth = dateCalender.get(Calendar.MONTH)+1;
            int  displayYear = dateCalender.get(Calendar.YEAR);
            int currentMonth = currentDate.get(Calendar.MONTH)+1;

            int  currentYear = currentDate.get(Calendar.YEAR);
            int TodayDate = currentDate.get(Calendar.DAY_OF_MONTH);
            int tDate = currentDate.get(Calendar.DATE);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.getDefault());
            String formattedDate = dateFormat.format(c);

            Calendar todayCalender = Calendar.getInstance();
            todayMonth=todayCalender.get(Calendar.MONTH)+1;
            //   Log.e("todayMonth", todayMonth+"");


            Date today = null;
            try {
                today =dateFormat.parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ((meetingCalenderAdaptor.viewHolder) holder).textView.setText(String.valueOf(DayNo));


            TodayCalender = Calendar.getInstance();
            TodayCalender.setTime(today);

            if(displayMonth == currentMonth && displayYear == currentYear){
                ((meetingCalenderAdaptor.viewHolder) holder).textView.setVisibility(View.VISIBLE);
                if(todayMonth == currentMonth) {
                    if (DayNo >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                        ((meetingCalenderAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#000000"));
                        //  Log.e("DayNo>", "currentMonth");
                    } else {
                        ((meetingCalenderAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#B3B2B2"));
                        // Log.e("DayNo>!", "currentMonth");
                    }
                }else if(todayMonth > currentMonth) {

                    ((meetingCalenderAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#B3B2B2"));
                    //   Log.e("todayMonth>", "currentMonth");
                }
                else {
                    ((meetingCalenderAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#000000"));
                    //   Log.e("only else", "yes");
                }


            }else {
                ((meetingCalenderAdaptor.viewHolder) holder).textView.setVisibility(View.INVISIBLE);
                //   DayNumber.setTextColor(Color.parseColor("#E4E4E4"));
            }

         ((meetingCalenderAdaptor.viewHolder)holder).bind(selectType, displayMonth,currentMonth,displayYear,currentYear, DayNo);
            /*btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalString.equals("0")){

                        StaticUtil.showIOSLikeDialog((Activity) mcontext, "Please select date");



                    }else {

                        Intent intent = new Intent(mcontext, SelectBookingSpace.class);
                        intent.putExtra("date",finalString );
                        intent.putExtra("datetype",selectType );
                        intent.putExtra("showdate",showSelectDate );
                        intent.putExtra("bayshowdate",baySelectDate );
                        intent.putExtra("multipleSelection",multipleSelection );
                        mcontext.startActivity(intent);
                        Log.e("date", finalString);
                        Log.e("showdate", showSelectDate);
                        Log.e("bayshowdate", baySelectDate);
                        Log.e("multipleSelection", multipleSelection+"");
                    }

                }
            });
*/


        } catch (Exception ex) {
            Log.d("Sri", "ex" + ex);
        }
    }


    @Override
    public int getItemCount() {

        return dates.size();
    }


    class viewHolder extends RecyclerView.ViewHolder  {


        TextView textView;


        public viewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.single_calendar_date);

        }



        public void bind(Boolean date, int displayMonth, int currentMonth, int displayYear, int currentYear, int dayNo) {

            if (date) {

                if (lastCheckedPos == -1) {

                    if(displayMonth == currentMonth && displayYear == currentYear){
                        textView.setVisibility(View.VISIBLE);
                        if(todayMonth == currentMonth) {
                            if (dayNo >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                                textView.setTextColor(Color.parseColor("#000000"));
                                //  Log.e("DayNo>", "currentMonth");
                            } else {
                                textView.setTextColor(Color.parseColor("#B3B2B2"));
                                // Log.e("DayNo>!", "currentMonth");
                            }
                        }else if(todayMonth > currentMonth) {

                            textView.setTextColor(Color.parseColor("#B3B2B2"));
                            //   Log.e("todayMonth>", "currentMonth");
                        }
                        else {
                            textView.setTextColor(Color.parseColor("#000000"));
                            //   Log.e("only else", "yes");
                        }
                        textView.setBackgroundResource(R.drawable.calender_select_bg);

                    }
                } else {
                    if (lastCheckedPos == getAdapterPosition()) {
                        textView.setBackgroundResource(R.drawable.selected_calender_type);
                        textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));

                    } else {


                        if(displayMonth == currentMonth && displayYear == currentYear){
                            textView.setVisibility(View.VISIBLE);
                            if(todayMonth == currentMonth) {
                                if (dayNo >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                                    textView.setTextColor(Color.parseColor("#000000"));
                                    //  Log.e("DayNo>", "currentMonth");
                                } else {
                                    textView.setTextColor(Color.parseColor("#B3B2B2"));
                                    // Log.e("DayNo>!", "currentMonth");
                                }
                            }else if(todayMonth > currentMonth) {

                                textView.setTextColor(Color.parseColor("#B3B2B2"));
                                //   Log.e("todayMonth>", "currentMonth");
                            }
                            else {
                                textView.setTextColor(Color.parseColor("#000000"));
                                //   Log.e("only else", "yes");
                            }


                        }




                        textView.setBackgroundResource(R.drawable.calender_select_bg);
                        //    textView.setTextColor(ContextCompat.getColor(mcontext, R.color.black));

                    }
                }


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String selectedDate = dateList.get(getAdapterPosition()).getDate();

                        DateTimeFormatter formatter = null;
                        DateTimeFormatter showFormatter = null;
                        LocalDate localDate = null;
                        LocalDate showlocalDate = null;
                        int selectMonth = 0;
                        int selectDay = 0;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );

                            localDate = LocalDate.parse( selectedDate , formatter );
                            //   showlocalDate = LocalDate.parse( selectedDate , showFormatter );
                            selectMonth = localDate.getMonthValue();
                            selectDay = localDate.getDayOfMonth();
                        }
                        //   Log.e("selectDay", selectDay+"");
                        //   Log.e("TodayCalender", TodayCalender.get(Calendar.DAY_OF_MONTH)+"");



                        if(todayMonth == currentMonth) {
                            if (selectDay >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                                textView.setBackgroundResource(R.drawable.selected_calender_type);
                                textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                                if (lastCheckedPos != getAdapterPosition()) {
                                    notifyItemChanged(lastCheckedPos);
                                    lastCheckedPos = getAdapterPosition();

                                    finalString   =  dateList.get(lastCheckedPos).getDate() + " 14:00";
                                    baySelectDate = dateList.get(lastCheckedPos).getDate();
                                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

                                    try {
                                        Date  oneWayTripDate = input.parse(finalString);                 // parse input
                                        showSelectDate = output.format(oneWayTripDate);    // format output
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                                //  Log.e("DayNo", "yes");
                            } else {
                                //   Log.e("DayNonot", "yes");
                            }
                            Log.e("click", "todayMonth == selectMonth");
                        }else if(todayMonth > currentMonth) {
                            //   Log.e("todayMonth", "yes");
                            Log.e("click", "todayMonth > selectMonth");
                        }
                        else {
                            Log.e("click", "else");
                            textView.setBackgroundResource(R.drawable.selected_calender_type);
                            textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                            if (lastCheckedPos != getAdapterPosition()) {
                                notifyItemChanged(lastCheckedPos);
                                lastCheckedPos = getAdapterPosition();
                                baySelectDate = dateList.get(lastCheckedPos).getDate();
                                finalString   =  dateList.get(lastCheckedPos).getDate()  + " 14:00";
                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

                                try {
                                    Date  oneWayTripDate = input.parse(finalString);                 // parse input
                                    showSelectDate = output.format(oneWayTripDate);    // format output
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }
                    /*  if(DayNo>= TodayCalender.get(Calendar.DAY_OF_MONTH)){*/




                    //  }

                });


            }else {

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*   if (DayNo >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {*/

                        String selectDate = dateList.get(getAdapterPosition()).getDate();

                        DateTimeFormatter formatter = null;
                        LocalDate localDate = null;
                        int selectMonth = 0;
                        int selectDay = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
                            localDate = LocalDate.parse( selectDate , formatter );
                            selectMonth = localDate.getMonthValue();
                            selectDay = localDate.getDayOfMonth();
                        }
                        //    Log.e("selectDay", selectDay+"");
                        //     Log.e("TodayCalender", TodayCalender.get(Calendar.DAY_OF_MONTH)+"");
                        if(todayMonth == selectMonth) {
                            if (selectDay >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {

                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

                                try {
                                    Date  oneWayTripDate = input.parse(dateList.get(getAdapterPosition()).getDate());                 // parse input
                                    showSelectDate = output.format(oneWayTripDate);    // format output
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                selectedDate.add(dateList.get(getAdapterPosition()).getDate()  + " 14:00");
                                baySelectedDate.add(dateList.get(getAdapterPosition()).getDate());
                                showselectedDate.add(showSelectDate);
                                textView.setBackgroundResource(R.drawable.selected_calender_type);
                                textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));

                                String listString = "";
                                for (String s : selectedDate) {

                                    listString += s + ",";
                                }

                                String showlistString = "";
                                for (String s : showselectedDate) {

                                    showlistString += s + "/";
                                }

                                String BaylistString = "";
                                for (String s : baySelectedDate) {

                                    BaylistString += s + ",";
                                }

                                finalString = listString.substring(0, listString.length() - 1);
                                showSelectDate = showlistString.substring(0, showlistString.length() - 1);
                                baySelectDate = BaylistString.substring(0, BaylistString.length() - 1);

                            } else {
                                //         Log.e("DayNonot", "yes");
                            }
                        }else if(todayMonth > selectMonth) {
                            //     Log.e("todayMonth", "yes");

                        }
                        else {

                            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                Date  oneWayTripDate = input.parse(dateList.get(getAdapterPosition()).getDate());                 // parse input
                                showSelectDate = output.format(oneWayTripDate);    // format output
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }



                            selectedDate.add(dateList.get(getAdapterPosition()).getDate()  + " 14:00");
                            baySelectedDate.add(dateList.get(getAdapterPosition()).getDate());
                            showselectedDate.add(showSelectDate);
                            textView.setBackgroundResource(R.drawable.selected_calender_type);
                            textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));

                            String listString = "";
                            for (String s : selectedDate) {

                                listString += s + ",";
                            }

                            String showlistString = "";
                            for (String s : showselectedDate) {

                                showlistString += s + " / ";
                            }
                            String BaylistString = "";
                            for (String s : baySelectedDate) {

                                BaylistString += s + ",";
                            }

                            finalString = listString.substring(0, listString.length() - 1);
                            showSelectDate = showlistString.substring(0, showlistString.length() - 1);
                            baySelectDate = BaylistString.substring(0, BaylistString.length() - 1);
                            //  Log.e("DayNo", "yes");
                        }








                        //   }
                    }
                });




            }
        }

       /* @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }*/
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Date getSelect(){

        if(lastCheckedPos != -1){
            return dates.get(lastCheckedPos);
        }
        return  null;
    }

}