package com.b2b.passwork.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.AvaialbleTimeSlot_Adaptor;
import com.b2b.passwork.Adaptor.DurationTimeAdaptor;
import com.b2b.passwork.Adaptor.GuestListAdator;
import com.b2b.passwork.Adaptor.SelectedEmployeeAdapter;
import com.b2b.passwork.Adaptor.workspaceDetailPageAdaptor;
import com.b2b.passwork.Model.AddGuestModel;
import com.b2b.passwork.Model.Employee.EmployeeResponse;
import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.MeetingBookResponse;
import com.b2b.passwork.Model.SeatBookResponse;
import com.b2b.passwork.Model.TimeDurationModel;
import com.b2b.passwork.Model.TimeslotModel;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkspaceDetail extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {


    Integer[] SheduleOfficeImage = new Integer[]{R.drawable.office_e, R.drawable.office_f};

    workspaceDetailPageAdaptor adapter;
    @BindView(R.id.view_pager2)
    ViewPager viewPager2;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; //
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.ImageBack)
    ImageView ImageBack;
    @BindView(R.id.back)
    FrameLayout back;
    @BindView(R.id.layout_Image)
    RelativeLayout layoutImage;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.parent)
    CoordinatorLayout parent;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;


    AvaialbleTimeSlot_Adaptor madapter;
    @BindView(R.id.TimeSlot)
    RecyclerView TimeSlot;
    DurationTimeAdaptor durationAdator;
    @BindView(R.id.TimeDuration)
    RecyclerView TimeDuration;
    @BindView(R.id.edt_Meeting)
    EditText edtMeeting;
    @BindView(R.id.selectedDate)
    TextView SelectedDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String RoomName, RoomDisc, CorporateId, Token, workspaceId, BayId;
    int maxSeat;
    @BindView(R.id.RoomName)
    TextView txtRoomName;
    @BindView(R.id.RoomDispriction)
    TextView txtRoomDispriction;
    @BindView(R.id.capacity)
    TextView capacity;
    @BindView(R.id.AddEmployee)
    Button AddEmployee;
    @BindView(R.id.AddGuest)
    Button AddGuest;
    @BindView(R.id.booking)
    Button booking;
    UserSessionManager session;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<EmployeesItem> Employees;
    List<EmployeesItem> SelectEmployees = new ArrayList<>();
    ArrayList<String> listofEmployee = new ArrayList<>();
    @BindView(R.id.TotalSelectedEmp)
    TextView TotalSelectedEmp;
    final List<String> selectedEmplyee = new ArrayList<>();
    final ArrayList<AddGuestModel> GuestList = new ArrayList<>();
    @BindView(R.id.AddGuestListview)
    RecyclerView AddGuestListview;
    GuestListAdator guestAdator;
    ArrayList<AddGuestModel> GuestListwithDetail = new ArrayList<>();
    String selected;
    String guestJson;
    List<TimeslotModel> your_array_list  = new ArrayList<TimeslotModel>();;
    List<TimeDurationModel> durationList = new ArrayList<TimeDurationModel>();;
    String StartTime, durationTime, selectedDate, EndTIme;
    SelectedEmployeeAdapter seletedEmplyee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.workspace_detail_layout);
        ButterKnife.bind(this);

        RoomName = getIntent().getStringExtra("roomName");
        RoomDisc = getIntent().getStringExtra("roomDis");
        BayId = getIntent().getStringExtra("BayId");
        maxSeat = getIntent().getIntExtra("capacity", 0);
        StartTime = getIntent().getStringExtra("startTime");
        EndTIme = getIntent().getStringExtra("EndTime");
        selectedDate = getIntent().getStringExtra("date");

        Log.e("BayId", BayId);

        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy");

        String newDateStr = postFormater.format(dateObj);

        SelectedDate.setText(newDateStr);
        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);

        HashMap<String, String> workSpaceDetail = session.getworkspaceList();

        workspaceId = workSpaceDetail.get(UserSessionManager.IS_WORKSPACE_ID);
        txtRoomName.setText(RoomName);
        txtRoomDispriction.setText(RoomDisc);
        capacity.setText("Capacity : " + maxSeat + " Max");

        SelectedDate.setOnClickListener(this);
        back.setOnClickListener(this);
        AddEmployee.setOnClickListener(this);
        AddGuest.setOnClickListener(this);
        booking.setOnClickListener(this);
        TotalSelectedEmp.setOnClickListener(this);

       /* Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        SelectedDate.setText(formattedDate);*/


        TimeslotModel model = new TimeslotModel("10:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("11:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("12:00", "AM", false);
        your_array_list.add(model);
        model = new TimeslotModel("01:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("02:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("03:00", "PM",  false);
        your_array_list.add(model);
        model = new TimeslotModel("04:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("05:00", "PM", false);
        your_array_list.add(model);
        model = new TimeslotModel("06:00", "PM", false);
        your_array_list.add(model);


        TimeDurationModel durationModel = new TimeDurationModel("00:30", "MIN", false);
        durationList.add(durationModel);
       durationModel = new TimeDurationModel("01:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("02:00", "HR",false);
        durationList.add(durationModel);

     durationModel = new TimeDurationModel("03:00",  "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("04:00", "HR",false);
        durationList.add(durationModel);
        durationModel = new TimeDurationModel("05:00", "HR", false);
        durationList.add(durationModel);
        durationModel = new TimeDurationModel("06:00", "HR", false);
        durationList.add(durationModel);

        durationModel = new TimeDurationModel("07:00", "HR", false);
        durationList.add(durationModel);


        madapter = new AvaialbleTimeSlot_Adaptor(your_array_list, this);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TimeSlot.setLayoutManager(horizontaLayoutManagaer);
        madapter.setOnItemClickListener(this);
        TimeSlot.setAdapter(madapter);

        guestAdator = new GuestListAdator(GuestList, this);
        LinearLayoutManager VerticalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AddGuestListview.setLayoutManager(VerticalLayoutManagaer1);
        guestAdator.setOnItemClickListener(WorkspaceDetail.this);
        AddGuestListview.setAdapter(guestAdator);

        AddGuestListview.setNestedScrollingEnabled(true);

        durationAdator = new DurationTimeAdaptor(durationList, this);
        LinearLayoutManager horizontaLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TimeDuration.setLayoutManager(horizontaLayoutManagaer1);
        durationAdator.setOnItemClickListener(this);
        TimeDuration.setAdapter(durationAdator);

        adapter = new workspaceDetailPageAdaptor(WorkspaceDetail.this, SheduleOfficeImage);
        viewPager2.setAdapter(adapter);

        indicator.setViewPager(viewPager2);
        indicator.animatePageSelected(0);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3 - 1) {
                    currentPage = 0;
                }
                viewPager2.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        getEmployee(CorporateId);


    }



    private void updateLabel() {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        SelectedDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.selectedDate:

                new DatePickerDialog(WorkspaceDetail.this, R.style.DialogThemeDatePricker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.AddGuest:
                AddGuestModel dataModel = new AddGuestModel("", "", "");
                GuestList.add(dataModel);
              /*  int position = GuestList.size();
                if(position>1) {
                    View view1 = AddGuestListview.getChildAt(position-1);
                    EditText nameEditText = (EditText) view1.findViewById(R.id.edt_GuestName);
                    EditText EmailEditText = (EditText) view1.findViewById(R.id.edt_Email);
                    EditText MobileEditText = (EditText) view1.findViewById(R.id.edt_Mobile);
                    String name = nameEditText.getText().toString();
                    String EmailId = EmailEditText.getText().toString();
                    String MobileNo = MobileEditText.getText().toString();



                    AddGuestModel model = new AddGuestModel(name, EmailId, MobileNo);
                    GuestList.add(model);

                }*/

                guestAdator.notifyDataSetChanged();



                break;

            case R.id.AddEmployee:
                selectEmployee();

                break;

            case R.id.back:

                finish();

                break;

            case R.id.booking:

                bookNowMeetingRoom();

                break;

            case R.id.TotalSelectedEmp:

                SelectEmployeeDetail();
                Log.e("work", "click");

                break;


        }
    }

    private void SelectEmployeeDetail() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_selected_employee);

        RecyclerView recyclerView = dialog.findViewById(R.id.selectedEmployee);
        ImageView closeImage = dialog.findViewById(R.id.closeDialog);

        seletedEmplyee = new SelectedEmployeeAdapter(SelectEmployees, this);
        LinearLayoutManager horizontaLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontaLayoutManagaer1);
        seletedEmplyee.setOnItemClickListener(this);
        recyclerView.setAdapter(seletedEmplyee);

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                TotalSelectedEmp.setText( SelectEmployees.size() + " Participants");

                for (int i = 0; i < SelectEmployees.size(); i++) {


                    // tvSelectedItemsPreview.setText(tvSelectedItemsPreview.getText() + selectedItems.get(i) + ", ");
                    if(i==0){
                        selected = SelectEmployees.get(i).getId()+ ",";
                    }  else {
                        selected = selected+SelectEmployees.get(i).getId()+ ",";
                    }
                }
            }
        });


        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();


        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();






    }

    private void bookNowMeetingRoom() {

        JSONObject jObjectData = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < GuestList.size(); i++) {
            View view = AddGuestListview.getChildAt(i);
            EditText nameEditText = (EditText) view.findViewById(R.id.edt_GuestName);
            EditText EmailEditText = (EditText) view.findViewById(R.id.edt_Email);
            EditText MobileEditText = (EditText) view.findViewById(R.id.edt_Mobile);
            String name = nameEditText.getText().toString();
            String EmailId = EmailEditText.getText().toString();
            String MobileNo = MobileEditText.getText().toString();
            try {
                jObjectData.put("name", name);
                jObjectData.put("email", EmailId);
                jObjectData.put("mobile", MobileNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jObjectData);
            int position = GuestList.size();
            GuestList.remove(position-1);
            AddGuestModel model = new AddGuestModel(name, EmailId, MobileNo);
            GuestList.add(model);

        }
        JSONObject finalobject = new JSONObject();
        try {
            finalobject.put("GuestList", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(GuestList.size()==0){
            guestJson = "";
        }else {
            guestJson =  finalobject.toString();
        }


        Log.e( "guestJson", guestJson);


        String emplSelect = "";

        if(selected.endsWith(",")) {

              emplSelect= selected.substring(0, selected.length() - 1);
        }
        Log.e("selected", emplSelect);



        bookseatAPI(BayId, workspaceId,  GuestList.size(), edtMeeting.getText().toString(),emplSelect , guestJson);
    }

    private void bookseatAPI(String bayId, String workspaceId, int size, String toString, String emplSelect, String guestJson) {

      //  progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        //    jsonParams.put("id", workspaceId);
        jsonParams.put("type", "meeting");
        jsonParams.put("workspace_id", workspaceId);
          jsonParams.put("bay_id", bayId);
          jsonParams.put("guest_count", size+"");
           jsonParams.put("meeting_topic", toString);
        jsonParams.put("guest_info", guestJson);
        jsonParams.put("employees", emplSelect);
        jsonParams.put("start_datetime", selectedDate+ " " + "14:00");
        jsonParams.put("end_datetime", selectedDate+ " " + "15:00");

        String json = jsonParams.toString();
        Log.e("json",json );

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<MeetingBookResponse> responseBody = RestManager.getInstance().getService()
                .getMEETINGBOOK(token, body);

        //"artist",
        responseBody.enqueue(new Callback<MeetingBookResponse>() {
            @Override
            public void onResponse(Call<MeetingBookResponse> call, Response<MeetingBookResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        MeetingBookResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            Intent intent = new Intent(WorkspaceDetail.this, SuccefullyDeskBook.class);
                            intent.putExtra("bookingNumber", response1.getBookingNumber());
                            intent.putExtra("startDate", selectedDate+ " " + StartTime);
                            intent.putExtra("endDate", selectedDate+ " " + EndTIme);
                            intent.putExtra("workspaceName", RoomName);
                            intent.putExtra("workspaceAddress", RoomDisc);
                            intent.putExtra("seatId", "Topic-" + toString);
                            startActivity(intent);

                            //  successbook(response1.getBookingNumber());


                        } else {

                            StaticUtil.showIOSLikeDialog(WorkspaceDetail.this, "Someting went wrong");
                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceDetail.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<MeetingBookResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceDetail.this, "Someting went wrong");
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
                        StaticUtil.showIOSLikeDialog(WorkspaceDetail.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceDetail.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });

    }

    private void selectEmployee() {

        final boolean[] checkedItems = new boolean[listofEmployee.size()];
        final List<String> selectedItems = listofEmployee;

        String[] mStringArray = listofEmployee.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkspaceDetail.this);

        // set the title for the alert dialog
        builder.setTitle("Select Employee");

        // set the icon for the alert dialog
        builder.setIcon(R.drawable.logo);

        // now this is the function which sets the alert dialog for multiple item selection ready
        builder.setMultiChoiceItems(mStringArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
                String currentItem = selectedItems.get(which);
            }
        });

        // alert dialog shouldn't be cancellable
        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        Log.e("checkedItem", checkedItems.length+"");
                      //  selectedEmplyee.add(selectedItems.get(i));
                        SelectEmployees.add(Employees.get(i));
                        Log.e("posion", i+"");
                       // tvSelectedItemsPreview.setText(tvSelectedItemsPreview.getText() + selectedItems.get(i) + ", ");

                    }

                }

                for (int i = 0; i < SelectEmployees.size(); i++) {


                        // tvSelectedItemsPreview.setText(tvSelectedItemsPreview.getText() + selectedItems.get(i) + ", ");
                        if(i==0){
                            selected = SelectEmployees.get(i).getId()+ ",";
                        }  else {
                            selected = selected+SelectEmployees.get(i).getId()+ ",";
                        }
                    }


                if (SelectEmployees.size() >= 1) {
                    TotalSelectedEmp.setText( SelectEmployees.size() + " Participants");
                    TotalSelectedEmp.setVisibility(View.VISIBLE);
                } else {
                    TotalSelectedEmp.setVisibility(View.INVISIBLE);
                }


            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // handle the neutral button of the dialog to clear
        // the selected items boolean checkedItem
       /* builder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                }
            }
        });
*/
        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onItemClick(View v, int position) {

        if(v.getId()==R.id.Delete)
        {
            GuestList.remove(position);
            guestAdator.notifyDataSetChanged();


        }

        if(v.getId()==R.id.Deleteimage)
        {
          //  selectedEmplyee.remove(position);
            SelectEmployees.remove(position);
            seletedEmplyee.notifyDataSetChanged();


        }
        if(v.getId()==R.id.txttimeSlot)
        {
            TextView TimeSlot = v.findViewById(R.id.txttimeSlot);


            for (int l = 0; l < your_array_list.size(); l++) {


                if (your_array_list.get(l).getSelected()) {
                    your_array_list.get(l).setSelected(false);
                    TimeSlot.setTextColor(getResources().getColor(R.color.black));

                    Log.e("seatId", your_array_list.get(l).getTimeSlot() + "");
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_book_btn) );
                    } else {
                        TimeSlot.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_book_btn));
                    }
                    madapter.notifyItemChanged(l);

                }
            }


            StartTime = your_array_list.get(position).getTimeSlot() + "";
            TimeSlot.setTextColor(getResources().getColor(R.color.white));
            your_array_list.get(position).setSelected(true);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                TimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_select_btn) );
            } else {
                TimeSlot.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_select_btn));
            }




        }  if(v.getId()==R.id.txttimeduration)
        {
            TextView TimeSlot = v.findViewById(R.id.txttimeduration);

            for (int l = 0; l < durationList.size(); l++) {


                if (durationList.get(l).getSelected()) {
                    durationList.get(l).setSelected(false);
                    TimeSlot.setTextColor(getResources().getColor(R.color.black));
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_book_btn) );
                    } else {
                        TimeSlot.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_book_btn));
                    }
                    Log.e("seatId", durationList.get(l).getTimeDuration() + "");
                    durationAdator.notifyItemChanged(l);

                }
            }



            durationTime = durationList.get(position).getTimeDuration() + "";
            TimeSlot.setTextColor(getResources().getColor(R.color.white));
            durationList.get(position).setSelected(true);

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                TimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_select_btn) );
            } else {
                TimeSlot.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_select_btn));
            }

          /*  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String currentDateandTime = StartTime;

            Date date = null;
            try {
                date = sdf.parse(currentDateandTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 1);*/

        }

    }


}
