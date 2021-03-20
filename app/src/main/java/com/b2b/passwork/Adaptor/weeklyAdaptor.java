package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.SelectBookingSpace;
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

public class weeklyAdaptor  extends RecyclerView.Adapter {

    List<Date> dates;
    Calendar currentDate;
    LayoutInflater inflater;
    List<dateModel> dateList = new ArrayList<>();
    List<String> selectedDate = new ArrayList<>();
    Context mcontext;
    private OnItemClickListener onItemClickListener;
    private static int lastCheckedPos = -1;
    Boolean selectType;
    Button btnConfirm;
    String finalString = "0";
    int todayMonth;

    Calendar TodayCalender;
    public weeklyAdaptor(Context context, List<Date> dates, Calendar calendar, Boolean selectionType, List<dateModel> dateList, Button confirmButton) {
        this.dates = dates;
        this.currentDate = calendar;
        inflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.selectType = selectionType;
        this.dateList = dateList;
        this.btnConfirm = confirmButton;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.single_calender, viewGroup, false);
        return new weeklyAdaptor.viewHolder(view);
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

            ((weeklyAdaptor.viewHolder) holder).textView.setText(String.valueOf(DayNo));


              TodayCalender = Calendar.getInstance();
            TodayCalender.setTime(today);

            // today date color
           /* if(DayNo==TodayCalender.get(Calendar.DAY_OF_MONTH) &&  displayMonth==TodayCalender.get(Calendar.MONTH)+1 && displayYear== TodayCalender.get(Calendar.YEAR)){

                ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#DC143C"));
            }*/

          /*  if (lastCheckedPos == -1) {
                ((weeklyAdaptor.viewHolder) holder).textView.setBackgroundResource(R.drawable.calender_select_bg);
                ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(ContextCompat.getColor(mcontext, R.color.black));
            } else {
                if (lastCheckedPos == position) {
                    ((weeklyAdaptor.viewHolder) holder).textView.setBackgroundResource(R.drawable.selected_calender_type);
                    ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                } else {

                    ((weeklyAdaptor.viewHolder) holder).textView.setBackgroundResource(R.drawable.calender_select_bg);
                    ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(ContextCompat.getColor(mcontext, R.color.black));
                }
            }*/
            if(displayMonth == currentMonth && displayYear == currentYear){
                ((weeklyAdaptor.viewHolder) holder).textView.setVisibility(View.VISIBLE);
                if(todayMonth == currentMonth) {
                    if (DayNo >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                        ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#000000"));
                      //  Log.e("DayNo>", "currentMonth");
                    } else {
                        ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#B3B2B2"));
                       // Log.e("DayNo>!", "currentMonth");
                    }
                }else if(todayMonth > currentMonth) {

                    ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#B3B2B2"));
                 //   Log.e("todayMonth>", "currentMonth");
                }
                else {
                    ((weeklyAdaptor.viewHolder) holder).textView.setTextColor(Color.parseColor("#000000"));
                 //   Log.e("only else", "yes");
                }


            }else {
                ((weeklyAdaptor.viewHolder) holder).textView.setVisibility(View.INVISIBLE);
                //   DayNumber.setTextColor(Color.parseColor("#E4E4E4"));
            }

            ((weeklyAdaptor.viewHolder)holder).bind(selectType, displayMonth,currentMonth,displayYear,currentYear, DayNo);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("finalString", finalString);
                    Intent intent = new Intent(mcontext, SelectBookingSpace.class);
                    intent.putExtra("date",finalString );
                    mcontext.startActivity(intent);


                }
            });



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
           /* if(!selectType) {
                textView.setOnClickListener(this);
            }*/
        }

        /*@Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }*/

        public void bind(Boolean date, int displayMonth, int currentMonth, int displayYear, int currentYear, int dayNo) {

            if (date) {

                if (lastCheckedPos == -1) {
                  //  textView.setBackgroundResource(R.drawable.calender_select_bg);
                 //   textView.setTextColor(ContextCompat.getColor(mcontext, R.color.black));

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
                        LocalDate localDate = null;
                        int selectMonth = 0;
                        int selectDay = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
                             localDate = LocalDate.parse( selectedDate , formatter );
                            selectMonth = localDate.getMonthValue();
                            selectDay = localDate.getDayOfMonth();
                        }
                     //   Log.e("selectDay", selectDay+"");
                     //   Log.e("TodayCalender", TodayCalender.get(Calendar.DAY_OF_MONTH)+"");



                            if(todayMonth == selectMonth) {
                                if (selectDay >= TodayCalender.get(Calendar.DAY_OF_MONTH)) {
                                    textView.setBackgroundResource(R.drawable.selected_calender_type);
                                    textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                                    if (lastCheckedPos != getAdapterPosition()) {
                                        notifyItemChanged(lastCheckedPos);
                                        lastCheckedPos = getAdapterPosition();

                                        finalString   =  dateList.get(lastCheckedPos).getDate();
                                      //  Log.e("lastCheckedPos", "yes");
                                    }
                                  //  Log.e("DayNo", "yes");
                                } else {
                                 //   Log.e("DayNonot", "yes");
                                }
                            }else if(todayMonth > selectMonth) {
                             //   Log.e("todayMonth", "yes");

                            }
                            else {
                              //  Log.e("todayMonthNotwork", "yes");
                                textView.setBackgroundResource(R.drawable.selected_calender_type);
                                textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                                if (lastCheckedPos != getAdapterPosition()) {
                                    notifyItemChanged(lastCheckedPos);
                                    lastCheckedPos = getAdapterPosition();

                                    finalString   =  dateList.get(lastCheckedPos).getDate();
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
                                selectedDate.add(dateList.get(getAdapterPosition()).getDate());
                                textView.setBackgroundResource(R.drawable.selected_calender_type);
                                textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));

                                String listString = "";
                                for (String s : selectedDate) {

                                    listString += s + ",";
                                }

                                finalString = listString.substring(0, listString.length() - 1);
                        //        Log.e("DayNo", "yes");
                            } else {
                       //         Log.e("DayNonot", "yes");
                            }
                        }else if(todayMonth > selectMonth) {
                       //     Log.e("todayMonth", "yes");

                        }
                        else {
                            selectedDate.add(dateList.get(getAdapterPosition()).getDate());
                            textView.setBackgroundResource(R.drawable.selected_calender_type);
                            textView.setTextColor(ContextCompat.getColor(mcontext, R.color.white));

                            String listString = "";
                            for (String s : selectedDate) {

                                listString += s + ",";
                            }

                            finalString = listString.substring(0, listString.length() - 1);
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