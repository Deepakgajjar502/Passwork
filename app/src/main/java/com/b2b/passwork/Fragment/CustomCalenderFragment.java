package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.b2b.passwork.Adaptor.MyGridAdapter;
import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Model.EventDetailModel;
import com.b2b.passwork.Model.Upcoming.UpComingResponse;
import com.b2b.passwork.Model.Upcoming.upcomingDataItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.LinePagerIndicatorDecoration;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomCalenderFragment extends Fragment {


    @BindView(R.id.Image)
    ImageView Image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.calendar_prev_button)
    ImageView calendarPrevButton;
    @BindView(R.id.calendar_date_display)
    TextView calendarDateDisplay;
    @BindView(R.id.calendar_next_button)
    ImageView calendarNextButton;
    @BindView(R.id.calendar_header)
    LinearLayout calendarHeader;
    @BindView(R.id.calendar_grid)
    GridView calendarGrid;
    MyGridAdapter myGridAdapter;
    private static final int MAX_CALENDAR_DAY = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    List<Date> dates = new ArrayList<>();
    UserSessionManager session;
    String CorporateId, Token, UserId;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    List<upcomingDataItem> UpcomingScheduleList;
    ArrayList<EventDetailModel> eventDetaiList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_calender, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        UserId = user.get(UserSessionManager.KEY_ID);
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

        getUpcomming(UserId);
        return view;
    }

    private void SetUpCalender() {

        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
        Log.e("todayDate", s.toString());


        String currentDate = dateFormat.format(calendar.getTime());
        calendarDateDisplay.setText(currentDate);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        while (dates.size() < MAX_CALENDAR_DAY) {

            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }




    }

    private void getUpcomming(String userId) {


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", userId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<UpComingResponse> responseBody = RestManager.getInstance().getService()
                .getHisotryBooking(token, body);

        //"artist",
        responseBody.enqueue(new Callback<UpComingResponse>() {
            @Override
            public void onResponse(Call<UpComingResponse> call, Response<UpComingResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        UpComingResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            UpcomingScheduleList = response1.getData();
                            if (UpcomingScheduleList != null) {

                                for (int i=0; i< UpcomingScheduleList.size(); i++){


                                    EventDetailModel model = new EventDetailModel();
                                    model.setEventDate(ConvertStringToEventDate(UpcomingScheduleList.get(i).getStartDatetime()));
                                    model.setEventTitle(UpcomingScheduleList.get(i).getSeats());
                                    model.setEventType(UpcomingScheduleList.get(i).getType());
                                    eventDetaiList.add(model);

                                }

                                myGridAdapter = new MyGridAdapter(getActivity(), dates, calendar,eventDetaiList );
                                calendarGrid.setAdapter(myGridAdapter);

                            } else {

                            }


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<UpComingResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    private Date ConvertStringToEventDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date date = null;

        try {
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }
}