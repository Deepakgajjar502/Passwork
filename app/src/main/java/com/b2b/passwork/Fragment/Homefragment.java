package com.b2b.passwork.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.Activity.SelectBookingMettingSpace;
import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Adaptor.WorkSpaceListAdaptor;
import com.b2b.passwork.Model.Upcoming.UpComingResponse;
import com.b2b.passwork.Model.Upcoming.upcomingDataItem;
import com.b2b.passwork.Model.WorkspaceList.WorkspacesItem;
import com.b2b.passwork.Model.WorkspaceList.workspaceListResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.PicassoHelper;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Homefragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.UserName)
    TextView UserName;
    @BindView(R.id.Profile)
    ImageView profile;


    String[] OfficeTitle = new String[]{"Community Poll", "Service Request", "Visitor Managment"};
    Integer[] BGColor = new Integer[]{R.color.service_color_a, R.color.service_color_c, R.color.service_color_b};

    Integer[] OfficeImage = new Integer[]{R.drawable.ic_trial_balloon, R.drawable.ic_authorized_dealer, R.drawable.ic_visitor_card};


    WorkSpaceListAdaptor Adaptor;

    @BindView(R.id.ListofShedule)
    RecyclerView ListWorkShedule;
    SheduleAdaptor sheduleAdaptor;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.round)
    TextView round;
    @BindView(R.id.ListWorkSpace)
    ViewPager2 ListWorkSpace;


    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    @BindView(R.id.sheducl)
    TextView sheducl;


    UserSessionManager session;
    String CorporateId, Token;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    List<WorkspacesItem> workspacesList;
    String UserId;
    List<upcomingDataItem> UpcomingScheduleList;
    @BindView(R.id.NoRecordUpcoming)
    LinearLayout NoRecordUpcoming;
    @BindView(R.id.ListofService)
    RecyclerView RecyService;
    @BindView(R.id.DeskBookingCardview)
    CardView DeskBookingCardview;
    @BindView(R.id.MeetingBookingCardview)
    CardView MeetingBookingCardview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        ButterKnife.bind(this, view);

        DeskBookingCardview.setOnClickListener(this);
        MeetingBookingCardview.setOnClickListener(this);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();

        String firstName = user.get(UserSessionManager.KEY_FIRST_NAME);
        String LastName = user.get(UserSessionManager.KEY_LAST_NAME);
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        UserId = user.get(UserSessionManager.KEY_ID);
        UserName.setText("Hello " + firstName + " " + LastName + " !");


        if (session.isFirstTimeLaunch()) {
            getWorkspaceList(CorporateId);
        }


        Picasso.get().load(R.drawable.progile)
                .transform(new PicassoHelper.PicassoCircleTransformation())
                .into(profile);

        Adaptor = new WorkSpaceListAdaptor(getActivity(), OfficeTitle, OfficeImage, BGColor);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyService.setLayoutManager(horizontaLayoutManagaer);
        RecyService.setAdapter(Adaptor);




        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };


        getUpcomming(UserId);


        return view;
    }

    private void getUpcomming(String userId) {


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", userId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<UpComingResponse> responseBody = RestManager.getInstance().getService()
                .getUpcommingBooking(token, body);

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
                                if (UpcomingScheduleList.size() != 0) {
                                    sheduleAdaptor = new SheduleAdaptor(getActivity(), UpcomingScheduleList);
                                    LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                    ListWorkShedule.setLayoutManager(horizontaLayoutManagaer);
                                    ListWorkShedule.setAdapter(sheduleAdaptor);
                                    //   ListWorkShedule.addItemDecoration(new LinePagerIndicatorDecoration());
                                    horizontaLayoutManagaer.findFirstVisibleItemPosition();
                                    ListWorkShedule.setVisibility(View.VISIBLE);
                                    NoRecordUpcoming.setVisibility(View.GONE);
                                } else {
                                    ListWorkShedule.setVisibility(View.GONE);
                                    NoRecordUpcoming.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ListWorkShedule.setVisibility(View.GONE);
                                NoRecordUpcoming.setVisibility(View.VISIBLE);
                            }


                        } else {
                            ListWorkShedule.setVisibility(View.GONE);
                            NoRecordUpcoming.setVisibility(View.VISIBLE);

                        }


                    } else {
                        ListWorkShedule.setVisibility(View.GONE);
                        NoRecordUpcoming.setVisibility(View.VISIBLE);
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
                ListWorkShedule.setVisibility(View.GONE);
                NoRecordUpcoming.setVisibility(View.VISIBLE);
            }
        });


    }

    private void getWorkspaceList(String corporateId) {

        progressBar.setVisibility(View.VISIBLE);


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("corporate_id", corporateId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<workspaceListResponse> responseBody = RestManager.getInstance().getService()
                .getWorkspaceList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<workspaceListResponse>() {
            @Override
            public void onResponse(Call<workspaceListResponse> call, Response<workspaceListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        workspaceListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            workspacesList = response1.getWorkspaces();
                            String workspaceID = workspacesList.get(0).getId();
                            String workspaceName = workspacesList.get(0).getName();
                            String workspaceAddress = workspacesList.get(0).getDisplayLocation();
                            Log.e("worksapace", response1.getWorkspaces().get(0).getId());
                            session.setFirstTimeLaunch(false);
                            session.setworkspaceDetail(workspaceID, workspaceName, workspaceAddress);


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<workspaceListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.DeskBookingCardview:


                Intent intent = new Intent(getContext(), BookDesk.class);
                startActivity(intent);
               // ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


                break;

            case R.id.MeetingBookingCardview:


                Intent intent1 = new Intent(getContext(), SelectBookingMettingSpace.class);
                startActivity(intent1);
                // ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


                break;

        }

    }
}