package com.b2b.passwork.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Model.Employee.EmployeeResponse;
import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.Room.BaysItem;
import com.b2b.passwork.Model.Room.GetRoomResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.TimePickerPopWin;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


public class BookMeeting extends Fragment implements View.OnClickListener {


    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.weekCalendar)
    WeekCalendar weekCalendar;
    @BindView(R.id.edt_select_workspace)
    EditText edtSelectWorkspace;
    @BindView(R.id.selectWorkspace)
    LinearLayout selectWorkspace;
    @BindView(R.id.swipe_btn)
    SwipeButton swipeBtn;
    UserSessionManager session;
    String WorkspaceId, Token, selectDate, workspaceName;
    @BindView(R.id.round)
    TextView round;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    floor_adaptor adaptor;
    List<FloorsItem> floors;
    ArrayList<String> listofFloorName = new ArrayList<>();

    List<BaysItem> rooms;
    ArrayList<String> listofRoom = new ArrayList<>();

    List<EmployeesItem> Employees;
    ArrayList<String> listofEmployee = new ArrayList<>();
    @BindView(R.id.edt_select_floor)
    EditText edtSelectFloor;
    @BindView(R.id.edt_select_capacity)
    EditText edtSelectCapacity;
    String CorporateId, maxCapacity, EmplopeeId;
    @BindView(R.id.selectStartTime)
    TextView selectStartTime;
    @BindView(R.id.selectEndTime)
    TextView selectEndTime;
    @BindView(R.id.cardview)
    CardView cardview;
    String StarTime, EndTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_meeting, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);

        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

                selectDate = dateTime.toLocalDate().toString();

                round.setText("Selected Date is : " + selectDate);
            }
        });

        edtSelectFloor.setOnClickListener(this);
        edtSelectCapacity.setOnClickListener(this);
        edtSelectWorkspace.setOnClickListener(this);
        selectStartTime.setOnClickListener(this);
        selectEndTime.setOnClickListener(this);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 30);

        DateFormat startTime = new SimpleDateFormat("hh:mm");
        String currentTime = startTime.format(Calendar.getInstance().getTime());
        SimpleDateFormat endTime = new SimpleDateFormat("hh:mm");

        Calendar nowAMPM = Calendar.getInstance();
        String AM_PM;
        if(nowAMPM.get(Calendar.AM_PM) == Calendar.AM){
            // AM
              AM_PM = "AM";

        }else{
            // PM
           AM_PM = "PM";

        }

        selectStartTime.setText(currentTime +" "+ AM_PM);
        selectEndTime.setText(endTime.format(now.getTime()) + " "+AM_PM );
      //  getFloorDetail(WorkspaceId);


        swipeBtn.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {

                bookMeatingAPI(EmplopeeId, WorkspaceId, selectDate, selectDate);

            }
        });

        getBay(WorkspaceId, "all");


        return view;
    }

    private void bookMeatingAPI(String emplopeeId, String workspaceId, String startDate, String EndDate) {




    }

    private void getFloorDetail(String workspaceId) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
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


                            //   adaptor = new floor_adaptor(getActivity(), floors, workspaceName, WorkspaceId);

                            for (int l = 0; l < floors.size(); l++) {

                                listofFloorName.add(floors.get(l).getFloorName());


                            }

                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<FloorListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    private void getEmployee(String corporateId) {


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", corporateId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<EmployeeResponse> responseBody = RestManager.getInstance().getService()
                .getEmployeeList(token, corporateId);

        //"artist",
        responseBody.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        EmployeeResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            Employees = response1.getEmployees();


                            //   adaptor = new floor_adaptor(getActivity(), floors, workspaceName, WorkspaceId);

                            for (int l = 0; l < Employees.size(); l++) {

                                listofEmployee.add(Employees.get(l).getFirstName() + " " + Employees.get(l).getLastName());


                            }

                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
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

            case R.id.edt_select_floor:


                selectFloorDailog();

                break;

            case R.id.edt_select_workspace:


                selectRoomDailog();

                break;

            case R.id.edt_select_capacity:


                selectEmployee();

                break;

            case R.id.selectStartTime:


                openTime("Start");

                break;

            case R.id.selectEndTime:


                openTime("End");

                break;


        }
    }

    private void openTime(String type) {


        TimePickerPopWin timePickerPopWin=new TimePickerPopWin.Builder(getActivity(), new TimePickerPopWin.OnTimePickListener() {
            @Override
            public void onTimePickCompleted(int hour, int minute, String AM_PM, String time) {

                if(type.equals("Start")) {
                    selectStartTime.setText(String.format("%02d", hour)+":"+String.format("%02d", minute)+ " "+AM_PM);

                }else {
                    selectEndTime.setText(String.format("%02d", hour)+":"+String.format("%02d", minute)+ " "+AM_PM);

                }


            }
        }).textConfirm("CONFIRM")
                .textCancel("CANCEL")
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(getActivity());


    }

    private void selectEmployee() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Employees");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice, listofEmployee);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSelectCapacity.setText("1 Selected / " + maxCapacity);
                EmplopeeId = Employees.get(which).getId() + "";
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();

                //  getBay(WorkspaceId, listofEmployee.get(which) + "" );

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void selectFloorDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Room");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice, listofFloorName);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSelectFloor.setText(listofFloorName.get(which));
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();



            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void selectRoomDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select floor");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice, listofRoom);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSelectWorkspace.setText(listofRoom.get(which));
                edtSelectCapacity.setText(rooms.get(which).getSeatsCount() + " Max");

                maxCapacity = rooms.get(which).getSeatsCount() + " Max";
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();
                getEmployee(CorporateId);


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getBay(String workspaceId, String floorId) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("floor_id", floorId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<GetRoomResponse> responseBody = RestManager.getInstance().getService()
                .getRoomList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<GetRoomResponse>() {
            @Override
            public void onResponse(Call<GetRoomResponse> call, Response<GetRoomResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        GetRoomResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            rooms = response1.getBays();

                            for (int l = 0; l < rooms.size(); l++) {

                                listofRoom.add(rooms.get(l).getBayName());

                            }


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<GetRoomResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }


}