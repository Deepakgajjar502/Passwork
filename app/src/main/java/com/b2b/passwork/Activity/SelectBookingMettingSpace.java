package com.b2b.passwork.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.b2b.passwork.Adaptor.CustomExpandableListAdapter;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewBaysItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewGetBayResponse;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewInventoryItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class SelectBookingMettingSpace extends AppCompatActivity implements View.OnClickListener {


    String selectedDate, showSelectedDate, baySelectedDate, Token, WorkspaceId;
    UserSessionManager session;
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    List<FloorsItem> floors;
    ArrayList<String> listofFloorName = new ArrayList<>();
    @BindView(R.id.selectFloor)
    TextView selectFloor;
    List<NewBaysItem> rooms = new ArrayList<>();
    List<NewInventoryItem> invetoryList = new ArrayList<>();
    Boolean selectType;
    CustomExpandableListAdapter adaptor;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    int lastExpandedposition = -1;
    String EndDate,StartDate;
 //   HashMap<String, List<NewInventoryItem>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_select_booking_metting_space);
        ButterKnife.bind(this);






        selectFloor.setOnClickListener(this);
        backButton.setOnClickListener(this);

        session = new UserSessionManager(this);
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);


        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);

        Calendar calendar = Calendar.getInstance();


        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


          EndDate = dateFormat.format(tomorrow);
       StartDate = dateFormat.format(tomorrow);

        listofFloorName.add("All Floors");
        getFloorDetail(WorkspaceId, StartDate, EndDate);
        getBay(WorkspaceId, "all", StartDate, EndDate);



    }

    private void getBay(String workspaceId, String floorId, String startDate, String endDate) {

        if (rooms != null) {
            rooms.clear();
        }
        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("floor_id", floorId);
        jsonParams.put("start_datetime", startDate);
        jsonParams.put("end_datetime", endDate);
        jsonParams.put("inventory_type", "Meeting Room");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<NewGetBayResponse> responseBody = RestManager.getInstance().getService()
                .getBayList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<NewGetBayResponse>() {
            @Override
            public void onResponse(Call<NewGetBayResponse> call, Response<NewGetBayResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        NewGetBayResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            rooms = response1.getBays();

                            adaptor = new CustomExpandableListAdapter(SelectBookingMettingSpace.this, rooms);
                            expandableListView.setAdapter(adaptor);
                           // LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(SelectBookingMettingSpace.this, LinearLayoutManager.VERTICAL, false);
                          //  expandableListView.setLayoutManager(horizontaLayoutManagaer);
                            //adaptor.setOnItemClickListener(SelectBookingSpace.this);

                            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                                @Override
                                public void onGroupExpand(int grpPosition) {


                                }
                            });

                            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                                @Override
                                public void onGroupCollapse(int groupPosition) {



                                }
                            });

                            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                @Override
                                public boolean onChildClick(ExpandableListView parent, View v,
                                                            int groupPosition, int childPosition, long id) {
                                    invetoryList = rooms.get(groupPosition).getInventory();

                                    Intent intent = new Intent(SelectBookingMettingSpace.this, MeetingSelectDate.class);
                                    intent.putExtra("roomName", invetoryList.get(childPosition).getBayName());
                                    intent.putExtra("floorName", rooms.get(groupPosition).getFloorInfo().get(0).getFloorName());
                                    intent.putExtra("roomDis", invetoryList.get(childPosition).getBayDescription());
                                    intent.putExtra("BayId", invetoryList.get(childPosition).getBayId()+"");
                                    intent.putExtra("capacity", invetoryList.get(childPosition).getSeatsCount()+"");
                                    startActivity(intent);


                                    return false;
                                }
                            });

                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(SelectBookingMettingSpace.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<NewGetBayResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(SelectBookingMettingSpace.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    private void getFloorDetail(String workspaceId, String startDate, String endDate) {

        // progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("start_datetime", startDate);
        jsonParams.put("end_datetime", endDate);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<FloorListResponse> responseBody = RestManager.getInstance().getService()
                .getFloorList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<FloorListResponse>() {
            @Override
            public void onResponse(Call<FloorListResponse> call, Response<FloorListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                //     progressBar.setVisibility(View.GONE);
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
                        StaticUtil.showIOSLikeDialog(SelectBookingMettingSpace.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<FloorListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                //    progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(SelectBookingMettingSpace.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.selectFloor:


                selectFloorDailog();

                break;

            case R.id.backButton:


                finish();


                break;
        }
    }

    private void selectFloorDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Floor");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice, listofFloorName);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectFloor.setText(listofFloorName.get(which));
                String floorid;
                if (which == 0) {
                    floorid = "all";
                } else {
                    floorid = floors.get(which - 1).getFloorId() + "";
                }
                Log.e("floorId", floorid);
                getBay(WorkspaceId, floorid, StartDate, EndDate);
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}