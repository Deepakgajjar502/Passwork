package com.b2b.passwork.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.EmployeeAdaptor;
import com.b2b.passwork.Adaptor.SeatListAdaptor;
import com.b2b.passwork.Adaptor.WorkSpaceListAdaptor;
import com.b2b.passwork.Model.Employee.EmployeeResponse;
import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.SeatBookResponses;
import com.b2b.passwork.Model.SeatList.AvailablityItem;
import com.b2b.passwork.Model.SeatList.SeatListResponse;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

public class WorkspaceLayout extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {


    String startDate, EndDate, workspaceId, floorId, floorName, UserId, InvetoryName, InvetoryID, CorporateId;

    UserSessionManager session;
    String Token, workspaceName, workspaceAddress;
    SeatListAdaptor adapter;
    List<SeatsItem> seats = new ArrayList<>();
    List<SeatsItem> bookSeats = new ArrayList<>();
    List<AvailablityItem> avaiableSeatDetail = new ArrayList<>();;
    List<AvailablityItem> avaiableSeatDetailFinal = new ArrayList<>();
    List<Integer> seatIdList;
    int nPrevSelGridItem = -1;
    String selectedItem;

    int maxSelectSeat = 1;
    String seatId = "";

    int lastItemSelectedPos = -1;
    List<EmployeesItem> Employees = new ArrayList<>();
    List<EmployeesItem> SelectEmployees = new ArrayList<>();
    ArrayList<String> listofEmployee = new ArrayList<>();

    Boolean multipleSelection = false;
    String baySelectDate;
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.floorDetail)
    TextView floorDetail;

    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.TotalSelectedEmp)
    TextView TotalSelectedEmp;
    @BindView(R.id.toggleLayout)
    RelativeLayout toggleLayout;
    @BindView(R.id.selectedDate)
    TextView selectedDate;


    @BindView(R.id.txtOccupiedSeat)
    TextView txtOccupiedSeat;


    @BindView(R.id.FloorSeatDetail)
    LinearLayout FloorSeatDetail;

    @BindView(R.id.grid)
    RecyclerView grid;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    String showDate;
    @BindView(R.id.floorName)
    TextView txtfloorName;
    @BindView(R.id.inventoryName)
    TextView inventoryName;
    EmployeeAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_workspace_layout);
        ButterKnife.bind(this);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        UserId = user.get(UserSessionManager.KEY_ID);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        HashMap<String, String> workspace = session.getworkspaceList();
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);
        workspaceAddress = workspace.get(UserSessionManager.IS_WORKSPACE_ADDRESS);
        workspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);

        startDate = getIntent().getStringExtra("SelectDate");
        EndDate = getIntent().getStringExtra("SelectDate");
        floorName = getIntent().getStringExtra("floorName");
        InvetoryName = getIntent().getStringExtra("InvName");
        InvetoryID = getIntent().getStringExtra("InvId");
        baySelectDate = getIntent().getStringExtra("baySelecteDate");
        showDate = getIntent().getStringExtra("showDate");
        multipleSelection = getIntent().getBooleanExtra("multipleSelection", false);

        backButton.setOnClickListener(this);

        Title.setText(workspaceName);
        address.setText(workspaceAddress);
        selectedDate.setText("Selected Date : " + showDate);
        txtfloorName.setText(floorName);
        inventoryName.setText(InvetoryName);
        grid.setLayoutManager(new GridLayoutManager(WorkspaceLayout.this, 6));

        floorDetail.setOnClickListener(this);

        getSeats(workspaceId, InvetoryID, baySelectDate, baySelectDate);

        if(multipleSelection) {
            getEmployee(CorporateId);
        }

        Log.e("multipleSelection", multipleSelection+"");


        //  bookseatAPI(seatId, workspaceId, startDate, EndDate);
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
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });






    }


    private void bookseatAPI(String seatId, String workspaceId, String startDate, String endDate) {


        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();

        jsonParams.put("type", "desk");
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("seats", seatId);
        jsonParams.put("employees", UserId);
        jsonParams.put("start_datetime", startDate);
        jsonParams.put("end_datetime", endDate);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SeatBookResponses> responseBody = RestManager.getInstance().getService()
                .getSAVEBOOK(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SeatBookResponses>() {
            @Override
            public void onResponse(Call<SeatBookResponses> call, Response<SeatBookResponses> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        SeatBookResponses response1 = response.body();

                        if (response1.getStatus().equals("1")) {

                            Intent intent = new Intent(WorkspaceLayout.this, SuccefullyDeskBook.class);
                            intent.putExtra("bookingNumber", response1.getBookingNumber().get(0));
                            intent.putExtra("startDate", showDate);
                            intent.putExtra("endDate", showDate);
                            intent.putExtra("workspaceName", workspaceName);
                            intent.putExtra("workspaceAddress", workspaceAddress);
                            intent.putExtra("seatId", seatId);
                            startActivity(intent);

                            //  successbook(response1.getBookingNumber());


                        } else {

                            StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, response1.getMessage());
                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SeatBookResponses> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error1", t.getMessage().toString());
            }
        });


    }


    private void getSeats(String workspaceId, String floorId, String startDate, String endDate) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();

        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("bay_id", floorId);
        jsonParams.put("start_datetime", startDate);
        jsonParams.put("end_datetime", endDate);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SeatListResponse> responseBody = RestManager.getInstance().getService()
                .getSeatList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SeatListResponse>() {
            @Override
            public void onResponse(Call<SeatListResponse> call, Response<SeatListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {
                        seats.clear();
                        avaiableSeatDetail.clear();
                        bookSeats.clear();

                        SeatListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            seats = response1.getSeats();
                            avaiableSeatDetail = response1.getAvailablity();
                            bookSeats =new ArrayList<>();



                            adapter = new SeatListAdaptor(WorkspaceLayout.this, seats);
                          adapter.setOnItemClickListener(WorkspaceLayout.this);
                            grid.setAdapter(adapter);

                            boolean breakLoop = false;

                            while (!breakLoop) {

                                for(int l=0; l<avaiableSeatDetail.size(); l++){

                                    if(!avaiableSeatDetail.get(l).isStatus()){
                                        breakLoop = true;
                                        avaiableSeatDetailFinal.add(avaiableSeatDetail.get(l));

                                    }else {

                                        if(avaiableSeatDetail.size()-1 ==l){
                                            breakLoop = true;
                                        }
                                    }

                                }

                                }




                                FloorSeatDetail.setVisibility(View.VISIBLE);
                            if (avaiableSeatDetailFinal!= null) {
                                //Do something after 100ms
                                txtOccupiedSeat.setText(avaiableSeatDetailFinal.size() + "");

                            }

                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SeatListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    @Override
    public void onItemClick(View v, int position) {

//        ImageView imageView = v.findViewById(R.id.grid_image);
          if (v.getId() == R.id.single_calendar_date) {


                if(multipleSelection){


                }else {

                   // DailogDeskBookDetail(seats.get(lastCheckedPos).getSeatId());
                }
            }

    }


     public void DailogDeskBookDetail(int seatId) {

        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.desk_book_dailog);

        SwipeButton btnBookNOw = (SwipeButton) dialog.findViewById(R.id.swipe_btn);
        TextView DeskTitle = (TextView) dialog.findViewById(R.id.deskTitle);
        TextView SelectedStartDate = (TextView) dialog.findViewById(R.id.BookingStartDate);
        TextView SelectedEndDate = (TextView) dialog.findViewById(R.id.BookingEndDate);
        TextView WorkspaceTitle = (TextView) dialog.findViewById(R.id.deskWorkspaceTitle);
        TextView WorkspaceAddress = (TextView) dialog.findViewById(R.id.deskWorkspaceAddress);
        TextView FloorName = (TextView) dialog.findViewById(R.id.FloorName);

        ImageView CLoseimage = (ImageView) dialog.findViewById(R.id.btn_close);

        WorkspaceTitle.setText(workspaceName);
        WorkspaceAddress.setText(workspaceAddress);
        DeskTitle.setText("Desk " + seatId);
        SelectedStartDate.setText("START DATE : " + showDate);
        /*if (startDate.equals(EndDate)) {

        } else {
            SelectedStartDate.setText("START DATE : " + startDate);
            SelectedEndDate.setText("END DATE : " + EndDate);
        }*/
        FloorName.setText(floorName);

        btnBookNOw.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {

                bookseatAPI(seatId+"", workspaceId, startDate, EndDate);

            }
        });

        CLoseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backButton:

                Intent intent = new Intent(WorkspaceLayout.this, BookDesk.class);
                startActivity(intent);

                break;

          /*  case R.id.floorDetail:

                bookDailog();

                break;*/

        }
    }


    public void onClickonSeat(int seatId) {
        Log.e("onCLick", "yes");

            if(multipleSelection){

                MulipleEmployeeBook(seatId);
                Log.e("multipleSelection", "true");
            }else {
                Log.e("multipleSelection", "falase");
                DailogDeskBookDetail(seatId);
            }




    }

    private void MulipleEmployeeBook(int seatId) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.select_employee_dailogbox);

        ImageView imageView = (ImageView)  dialog.findViewById(R.id.cancelImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RecyclerView recyclerView = dialog.findViewById(R.id.employeeList);


        adaptor = new EmployeeAdaptor(this, Employees);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontaLayoutManagaer);
        recyclerView.setAdapter(adaptor);


        /*TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog.show();









    }
}