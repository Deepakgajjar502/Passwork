package com.b2b.passwork.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.WorkspaceList.workspaceListResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.LinePagerIndicatorDecoration;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDesk extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.Calender)
    ImageView Calender;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.weekCalendar)
    WeekCalendar weekCalendar;
    @BindView(R.id.workspaceDetail)
    LinearLayout workspaceDetail;
    UserSessionManager session;
    MaterialDatePicker materialDatePicker;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_desk);
        ButterKnife.bind(this);

        backButton.setOnClickListener(this);
        Calender.setOnClickListener(this);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);


        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


     EndDate = dateFormat.format(tomorrow);
        StartDate = dateFormat.format(tomorrow);

        selectDate.setText("Selected Date is : " +EndDate);

        getFloorDetail(WorkspaceId);

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {



                StartDate = dateTime.toLocalDate().toString();
                EndDate = dateTime.toLocalDate().toString();


                selectDate.setText("Selected Date is : " +EndDate);
                getFloorDetail(WorkspaceId);

            }
        });

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {


                        selectDate.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        Pair selectedDates = (Pair) materialDatePicker.getSelection();
//              then obtain the startDate & endDate from the range
                        final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                        Date startDate = rangeDate.first;
                        Date endDate = rangeDate.second;
//              Format the dates in ur desired display mode
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
//              Display it by setText

                        StartDate = simpleFormat.format(startDate);
                        EndDate = simpleFormat.format(endDate);
                        Log.e("StartDate", StartDate);

                    }
                });
    }

    private void getFloorDetail(String workspaceId) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("start_datetime", StartDate);
        jsonParams.put("end_datetime", EndDate);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<FloorListResponse> responseBody = RestManager.getInstance().getService()
                .getFloorList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<FloorListResponse>() {
            @Override
            public void onResponse(Call<FloorListResponse> call, Response<FloorListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        FloorListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            floors = response1.getFloors();
                            adaptor = new floor_adaptor(BookDesk.this,floors, workspaceName,WorkspaceId );
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(BookDesk.this, LinearLayoutManager.VERTICAL, false);
                            floorDetail.setLayoutManager(horizontaLayoutManagaer);
                            adaptor.setOnItemClickListener(BookDesk.this);
                            floorDetail.setAdapter(adaptor);


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(BookDesk.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<FloorListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(BookDesk.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.backButton:
                Log.e("click", "working");

                Intent intent = new Intent(BookDesk.this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.Calender:

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                //do your stuff
                break;

        }


    }

    @Override
    public void onItemClick(View v, int position) {


            if(v.getId()==R.id.workspace)
            {
                if(StartDate.equals("")){
                    StaticUtil.showIOSLikeDialog(BookDesk.this, "Please Select Date");

                }else {

                    String floor_name = floors.get(position).getFloorName();
                    Intent intent = new Intent(BookDesk.this, WorkspaceLayout.class);
                    intent.putExtra("workspace_id",WorkspaceId );
                    intent.putExtra("floor_id", floors.get(position).getFloorId()+"" );
                    intent.putExtra("startDate",StartDate );
                    intent.putExtra("endDate",EndDate );
                    intent.putExtra("floor_name",floor_name );
                    intent.putExtra("FloorList",(Serializable)floors );
                    startActivity(intent);
                }


            }

        }


}