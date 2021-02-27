package com.b2b.passwork.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.SeatListAdaptor;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.SeatBookResponse;
import com.b2b.passwork.Model.SeatList.SeatListResponse;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class WorkspaceLayout extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.officeLayout)
    ImageView officeLayout;
    @BindView(R.id.grid)
    RecyclerView grid;
    String startDate, EndDate, workspaceId, floorId, floorName;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    UserSessionManager session;
    String Token, workspaceName, workspaceAddress;
    SeatListAdaptor adapter;
    List<SeatsItem> seats;
    List<SeatsItem> bookSeats;
    int nPrevSelGridItem = -1;
    String selectedItem;
    @BindView(R.id.backButton)
    ImageView backButton;
    int maxSelectSeat = 1;
    String seatId = "";
    int selectedItemPos = -1;
    int lastItemSelectedPos = -1;
    BottomSheetBehavior sheetBehavior;
    LinearLayout StatusBottomSheet;
    @BindView(R.id.txtTotalSeat)
    TextView txtTotalSeat;
    @BindView(R.id.txtOccupiedSeat)
    TextView txtOccupiedSeat;
    @BindView(R.id.txtAvaialbleSeat)
    TextView txtAvaialbleSeat;
    @BindView(R.id.FloorSeatDetail)
    LinearLayout FloorSeatDetail;
    ArrayList<FloorsItem> floors = new ArrayList<>();
    @BindView(R.id.floorDetail)
    TextView floorDetail;
    @BindView(R.id.selectedDate)
    TextView selectedDate;
    ArrayAdapter arrayAdapter;
    ArrayList<String> listofFloorName = new ArrayList<>();
    @BindView(R.id.Calender)
    ImageView Calender;
    @BindView(R.id.weekCalendar)
    WeekCalendar weekCalendar;
    MaterialDatePicker materialDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace_layout);
        ButterKnife.bind(this);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        HashMap<String, String> workspace = session.getworkspaceList();
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);
        workspaceAddress = workspace.get(UserSessionManager.IS_WORKSPACE_ADDRESS);

        startDate = getIntent().getStringExtra("startDate");
        EndDate = getIntent().getStringExtra("endDate");
        workspaceId = getIntent().getStringExtra("workspace_id");
        floorId = getIntent().getStringExtra("floor_id");
        floorName = getIntent().getStringExtra("floor_name");
        floors = (ArrayList<FloorsItem>) getIntent().getSerializableExtra("FloorList");
        StatusBottomSheet = findViewById(R.id.DeskBookLayout);
        sheetBehavior = BottomSheetBehavior.from(StatusBottomSheet);
        sheetBehavior.setPeekHeight(0);
        backButton.setOnClickListener(this);
        Calender.setOnClickListener(this);
        Title.setText(workspaceName);
        address.setText(workspaceAddress);
        grid.setLayoutManager(new GridLayoutManager(WorkspaceLayout.this, 6));
        floorDetail.setText(floorName);
        floorDetail.setOnClickListener(this);
        selectedDate.setText("Selected Date : " + startDate + " to " + EndDate);

        getSeats(workspaceId, floorId, startDate, EndDate);

        for (int l = 0; l < floors.size(); l++) {

            listofFloorName.add(floors.get(l).getFloorName());


        }


        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {



                startDate = dateTime.toLocalDate().toString();
                EndDate = dateTime.toLocalDate().toString();


                selectedDate.setText("Selected Date : " + startDate + " to " + EndDate);

                getSeats(workspaceId, floorId, startDate, EndDate);
            }
        });

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {


                        selectedDate.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        Pair selectedDates = (Pair) materialDatePicker.getSelection();
//              then obtain the startDate & endDate from the range
                        final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                        Date StartDate = rangeDate.first;
                        Date endDate = rangeDate.second;
//              Format the dates in ur desired display mode
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
//              Display it by setText

                        startDate = simpleFormat.format(StartDate);
                        EndDate = simpleFormat.format(endDate);
                        getSeats(workspaceId, floorId, startDate, EndDate);

                    }
                });


        //  bookseatAPI(seatId, workspaceId, startDate, EndDate);
    }


    private void bookseatAPI(String seatId, String workspaceId, String startDate, String endDate) {


        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", workspaceId);
        jsonParams.put("type", "desk");
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("facility_id", "1");
        jsonParams.put("guest_count", "0");
        jsonParams.put("meeting_topic", "asd");
        jsonParams.put("seats", seatId);
        jsonParams.put("guest_info", "");
        jsonParams.put("start_datetime", startDate);
        jsonParams.put("end_datetime", endDate);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SeatBookResponse> responseBody = RestManager.getInstance().getService()
                .getSAVEBOOK(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SeatBookResponse>() {
            @Override
            public void onResponse(Call<SeatBookResponse> call, Response<SeatBookResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        SeatBookResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            Intent intent = new Intent(WorkspaceLayout.this, SuccefullyDeskBook.class);
                            intent.putExtra("bookingNumber", response1.getBookingNumber());
                            intent.putExtra("startDate", startDate);
                            intent.putExtra("endDate", endDate);
                            intent.putExtra("workspaceName", workspaceName);
                            intent.putExtra("workspaceAddress", workspaceAddress);
                            intent.putExtra("seatId", seatId);
                            startActivity(intent);

                            //  successbook(response1.getBookingNumber());


                        } else {

                            StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SeatBookResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    private void successbook(String bookingNumber) {

        final iOSDialog iOSDialog = new iOSDialog(this);
        iOSDialog.setTitle(this.getString(R.string.app_name));
        iOSDialog.setSubtitle("Your Desk Booked Successfully, Booking Number #" + bookingNumber);
        iOSDialog.setPositiveLabel("OK");

        iOSDialog.setBoldPositiveLabel(true);

        iOSDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkspaceLayout.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                iOSDialog.dismiss();
            }
        });
        iOSDialog.show();


    }

    private void getSeats(String workspaceId, String floorId, String startDate, String endDate) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("floor_id", floorId);
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

                        SeatListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            seats = response1.getSeats();
                            bookSeats = new ArrayList<>();
                            adapter = new SeatListAdaptor(WorkspaceLayout.this, seats);
                            adapter.setOnItemClickListener(WorkspaceLayout.this);
                            grid.setAdapter(adapter);
                            txtTotalSeat.setText(seats.size()+"" );

                            for (int l = 0; l < seats.size(); l++) {


                                if (!seats.get(l).isAvailable()) {
                                    // Log.e("Avaiable", seats.get(l).isAvailable()+"");
                                    bookSeats.add(seats.get(l));
                                }

                            }

                            FloorSeatDetail.setVisibility(View.VISIBLE);
                            if (bookSeats != null) {
                                //Do something after 100ms
                                txtOccupiedSeat.setText(bookSeats.size() + "");
                                txtAvaialbleSeat.setText((seats.size() - bookSeats.size()) + "");
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

        ImageView imageView = v.findViewById(R.id.grid_image);
        if (v.getId() == R.id.seatLayout) {


            for (int l = 0; l < seats.size(); l++) {


                if (seats.get(l).getSelected()) {
                    seats.get(l).setSelected(false);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_armchair));
                    imageView.setColorFilter(ContextCompat.getColor(this, R.color.white));
                    Log.e("seatId", seats.get(l).getSeatId() + "");
                    adapter.notifyItemChanged(l);
                }
            }


            seatId = seats.get(position).getSeatId() + "";
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_armchair_selected));
            seats.get(position).setSelected(true);
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue));

               //  bookDailog();

             toggleBottomSheet();

        }
    }

    private void bookDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Floor");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice, listofFloorName);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();
                getSeats(workspaceId, floors.get(which).getFloorId() + "", startDate, EndDate);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backButton:

                Intent intent = new Intent(WorkspaceLayout.this, BookDesk.class);
                startActivity(intent);

                break;

            case R.id.floorDetail:

                bookDailog();

                break;
            case R.id.Calender:

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                //do your stuff
                break;
        }
    }

    private void toggleBottomSheet() {

        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            sheetBehavior.setPeekHeight(0);


        } else {
            // sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //sheetBehavior.setPeekHeight(0);

        }


        SwipeButton btnBookNOw = (SwipeButton) findViewById(R.id.swipe_btn);
        TextView DeskTitle = (TextView) findViewById(R.id.deskTitle);
        TextView SelectedStartDate = (TextView) findViewById(R.id.BookingStartDate);
        TextView SelectedEndDate = (TextView) findViewById(R.id.BookingEndDate);
        TextView WorkspaceTitle = (TextView) findViewById(R.id.deskWorkspaceTitle);
        TextView WorkspaceAddress = (TextView) findViewById(R.id.deskWorkspaceAddress);
        TextView FloorName = (TextView) findViewById(R.id.FloorName);

        ImageView CLoseimage = (ImageView) findViewById(R.id.btn_close);

        WorkspaceTitle.setText(workspaceName);
        WorkspaceAddress.setText(workspaceAddress);
        DeskTitle.setText("Desk " + seatId);
        SelectedStartDate.setText("START DATE : " + startDate +" to " + "END DATE : " + EndDate);
      //  SelectedEndDate.setText("END DATE : " + EndDate);
        FloorName.setText(floorName);

        btnBookNOw.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {

                bookseatAPI(seatId, workspaceId, startDate, EndDate);

            }
        });

        CLoseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


    }
}