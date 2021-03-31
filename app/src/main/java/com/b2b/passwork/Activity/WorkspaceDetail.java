package com.b2b.passwork.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.GuestListAdator;
import com.b2b.passwork.Adaptor.SelectedEmployeeAdapter;
import com.b2b.passwork.Adaptor.workspaceDetailPageAdaptor;
import com.b2b.passwork.Model.AddGuestModel;
import com.b2b.passwork.Model.Employee.EmployeeResponse;
import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.MeetingBookResponse;
import com.b2b.passwork.Model.TimeDurationModel;
import com.b2b.passwork.Model.TimeslotModel;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

import static java.lang.Integer.parseInt;

public class WorkspaceDetail extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {


    Integer[] SheduleOfficeImage = new Integer[]{R.drawable.office_e, R.drawable.office_f};

    workspaceDetailPageAdaptor adapter;

    @BindView(R.id.ImageBack)
    ImageView ImageBack;

    @BindView(R.id.layout_Image)
    RelativeLayout layoutImage;


    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;


    @BindView(R.id.edt_Meeting)
    TextView edtMeeting;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String RoomName, RoomDisc, CorporateId, Token, workspaceId, BayId, maxSeat;

    @BindView(R.id.RoomName)
    TextView txtRoomName;
    @BindView(R.id.RoomDispriction)
    TextView txtRoomDispriction;
    ;

    @BindView(R.id.booking)
    Button booking;
    UserSessionManager session;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<EmployeesItem> Employees;
    List<EmployeesItem> SelectEmployees = new ArrayList<>();
    ArrayList<String> listofEmployee = new ArrayList<>();

    final List<String> selectedEmplyee = new ArrayList<>();
    final ArrayList<AddGuestModel> GuestList = new ArrayList<>();
    @BindView(R.id.AddGuestListview)
    RecyclerView AddGuestListview;
    GuestListAdator guestAdator;
    ArrayList<AddGuestModel> GuestListwithDetail = new ArrayList<>();
    String selected;
    String guestJson;
    List<TimeslotModel> your_array_list = new ArrayList<TimeslotModel>();

    List<TimeDurationModel> durationList = new ArrayList<TimeDurationModel>();

    String StartTime, durationTime, selectedDate, EndTIme, floorName;
    SelectedEmployeeAdapter seletedEmplyee;
    @BindView(R.id.txtStartTime)
    TextView txtStartTime;
    @BindView(R.id.txtDuration)
    TextView txtDuration;
    @BindView(R.id.txtcapacity)
    TextView txtcapacity;
    @BindView(R.id.FloorName)
    TextView FloorName;
    @BindView(R.id.txtSelectedDate)
    TextView txtSelectedDate;
    @BindView(R.id.imgMeetingAgenda)
    ImageView imgMeetingAgenda;
    @BindView(R.id.edtMeetingAganda)
    EditText edtMeetingAganda;
    @BindView(R.id.editMeetingLayout)
    RelativeLayout editMeetingLayout;
    @BindView(R.id.txtProgress)
    TextView txtProgress;

    @BindView(R.id.prograssLAyout)
    RelativeLayout prograssLAyout;
    @BindView(R.id.meetingTItleLayout)
    RelativeLayout meetingTItleLayout;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.txtAdd_Participants)
    TextView txtAddParticipants;
    @BindView(R.id.txtAdd_Guest)
    TextView txtAddGuest;
    @BindView(R.id.BtnaddParticipants)
    ImageView BtnaddParticipants;
    @BindView(R.id.txtProgressAddEmployee)
    TextView txtProgressAddEmployee;
    @BindView(R.id.prograssAddEmployee)
    RelativeLayout prograssAddEmployee;
    @BindView(R.id.AddGuestBtn)
    ImageView AddGuestBtn;
    @BindView(R.id.edt_GuestName)
    EditText edtGuestName;
    @BindView(R.id.edt_Email)
    EditText edtEmail;
    @BindView(R.id.edt_Mobile)
    EditText edtMobile;
    @BindView(R.id.btnAddGuestDetail)
    Button BtnAddGuestDetail;
    @BindView(R.id.GuestLayout)
    LinearLayout GuestLayout;
    @BindView(R.id.addGuestLayout)
    RelativeLayout addGuestLayout;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.Detail)
    TextView Detail;
    @BindView(R.id.linea)
    View linea;
    @BindView(R.id.SelectDateLayout)
    RelativeLayout SelectDateLayout;
    @BindView(R.id.startTimeLayout)
    RelativeLayout startTimeLayout;
    @BindView(R.id.durationLayout)
    RelativeLayout durationLayout;
    @BindView(R.id.capacityLayout)
    RelativeLayout capacityLayout;
    @BindView(R.id.AddParticipantLayout)
    RelativeLayout AddParticipantLayout;
    @BindView(R.id.textGuestName)
    TextInputLayout textGuestName;
    @BindView(R.id.textmail)
    TextInputLayout textmail;
    @BindView(R.id.textMobile)
    TextInputLayout textMobile;
    @BindView(R.id.txtProgressGuest)
    TextView txtProgressGuest;
    @BindView(R.id.prograssAddGuest)
    RelativeLayout prograssAddGuest;
    String timeType;
    @BindView(R.id.prograssAddGuestLayout)
    RelativeLayout prograssAddGuestLayout;
    String sendTime;
      boolean[] checkedItems;
       List<String> selectedItems = new ArrayList<>();
    String[] mStringArray;
    ArrayList<Integer> mUserItems = new ArrayList<>();



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
        maxSeat = getIntent().getStringExtra("capacity");
        StartTime = getIntent().getStringExtra("startTime");
        EndTIme = getIntent().getStringExtra("EndTime");
        selectedDate = getIntent().getStringExtra("date");
        floorName = getIntent().getStringExtra("floorName");
        timeType = getIntent().getStringExtra("timeType");

        if(timeType.equals("PM")) {

           String[] hours = StartTime.split(":");
            String  hourtime = hours[0];



            int  hour = parseInt(hourtime, 10) + 12;


            sendTime = hour+":00";
        }else {
            sendTime = StartTime;
        }
        Log.e("BayId",BayId+"" );
        txtcapacity.setText(maxSeat);
        txtStartTime.setText(StartTime + " " + timeType);
        txtDuration.setText(EndTIme + " HR");
        FloorName.setText(floorName);


        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy");

        String newDateStr = postFormater.format(dateObj);
        txtSelectedDate.setText(newDateStr);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);

        HashMap<String, String> workSpaceDetail = session.getworkspaceList();

        workspaceId = workSpaceDetail.get(UserSessionManager.IS_WORKSPACE_ID);
        txtRoomName.setText(RoomName);
        txtRoomDispriction.setText(RoomDisc);

        //capacity.setText("Capacity : " + maxSeat + " Max");


        booking.setOnClickListener(this);
        meetingTItleLayout.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        prograssLAyout.setOnClickListener(this);
        edtMeeting.setOnClickListener(this);
        imgMeetingAgenda.setOnClickListener(this);
        txtAddParticipants.setOnClickListener(this);
        BtnaddParticipants.setOnClickListener(this);
        txtProgressAddEmployee.setOnClickListener(this);
        prograssAddEmployee.setOnClickListener(this);
        txtProgress.setOnClickListener(this);
        AddGuestBtn.setOnClickListener(this);
        BtnAddGuestDetail.setOnClickListener(this);
        txtAddGuest.setOnClickListener(this);
        txtProgressGuest.setOnClickListener(this);


        guestAdator = new GuestListAdator(GuestList, this);
        LinearLayoutManager VerticalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        AddGuestListview.setLayoutManager(VerticalLayoutManagaer1);
        guestAdator.setOnItemClickListener(WorkspaceDetail.this);
        AddGuestListview.setAdapter(guestAdator);

        AddGuestListview.setNestedScrollingEnabled(true);


        getEmployee(CorporateId);


    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.selectedDate:

                new DatePickerDialog(WorkspaceDetail.this, R.style.DialogThemeDatePricker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.meetingTItleLayout:

                imgMeetingAgenda.setVisibility(View.INVISIBLE);
                //  editMeetingLayout.setVisibility(View.VISIBLE);

                showView(editMeetingLayout);

                break;

            case R.id.edt_Meeting:

                //   imgMeetingAgenda.setVisibility(View.INVISIBLE);
                //     editMeetingLayout.setVisibility(View.VISIBLE);
                hideView(imgMeetingAgenda);
                showView(editMeetingLayout);


                break;

            case R.id.imgMeetingAgenda:

                //   imgMeetingAgenda.setVisibility(View.INVISIBLE);
                //   editMeetingLayout.setVisibility(View.VISIBLE);

                hideView(editMeetingLayout);
                showView(imgMeetingAgenda);

                break;

            case R.id.btnAdd:

                hideView(editMeetingLayout);

                prograssLAyout.setVisibility(View.VISIBLE);
                txtProgress.setText(edtMeetingAganda.getText().toString().trim());

                break;

            case R.id.prograssLAyout:


                hideView(prograssLAyout);
                showView(editMeetingLayout);

                break;

            case R.id.txtProgress:


                hideView(prograssLAyout);
                showView(editMeetingLayout);

                break;

            case R.id.txtAdd_Participants:

                customSelectEmployee();
                //selectEmployee();

                break;
            case R.id.BtnaddParticipants:

                //selectEmployee();

                break;


            case R.id.prograssAddEmployee:

                SelectEmployeeDetail();

                break;


            case R.id.txtProgressAddEmployee:

                SelectEmployeeDetail();

                break;


            case R.id.AddGuestBtn:

                showView(GuestLayout);
                if (prograssAddGuest.getVisibility() == View.VISIBLE) {
                    prograssAddGuest.setVisibility(View.GONE);
                    // Its visible
                }
                if (prograssAddGuestLayout.getVisibility() == View.VISIBLE) {
                    prograssAddGuestLayout.setVisibility(View.GONE);
                    // Its visible
                }
                //   SelectEmployeeDetail();

                break;

            case R.id.txtAdd_Guest:

                showView(GuestLayout);
                if (prograssAddGuest.getVisibility() == View.VISIBLE) {
                    prograssAddGuest.setVisibility(View.GONE);
                    // Its visible
                }
                if (prograssAddGuestLayout.getVisibility() == View.VISIBLE) {
                    prograssAddGuestLayout.setVisibility(View.GONE);
                    // Its visible
                }
                //  SelectEmployeeDetail();

                break;
            case R.id.btnAddGuestDetail:

                hideView(GuestLayout);
                String name = edtGuestName.getText().toString();
                String EmailId = edtEmail.getText().toString();
                String MobileNo = edtMobile.getText().toString();
                AddGuestModel model = new AddGuestModel(name, EmailId, MobileNo);
                GuestList.add(model);
                //  SelectEmployeeDetail();
                txtProgressGuest.setText(GuestList.size() + " Guest");
                prograssAddGuest.setVisibility(View.VISIBLE);
                edtGuestName.setText("");
                edtEmail.setText("");
                edtMobile.setText("");

                break;

            case R.id.txtProgressGuest:

                prograssAddGuestLayout.setVisibility(View.VISIBLE);
                prograssAddGuest.setVisibility(View.GONE);
                break;




            case R.id.back:

                finish();

                break;

            case R.id.booking:

                bookNowMeetingRoom();

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
                //  TotalSelectedEmp.setText(SelectEmployees.size() + " Participants");
                txtProgressAddEmployee.setText(SelectEmployees.size() + " Participants");
                for (int i = 0; i < SelectEmployees.size(); i++) {


                    // txtProgressAddEmployee.setText(txtProgressAddEmployee.getText() + listofEmployee.get(i) + ", ");
                    if (i == 0) {
                        selected = SelectEmployees.get(i).getId() + ",";
                    } else {
                        selected = selected + SelectEmployees.get(i).getId() + ",";
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


        if (GuestList.size() == 0) {
            guestJson = "";
        } else {
            JSONObject jObjectData = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < GuestList.size(); i++) {

                try {
                    jObjectData.put("name", GuestList.get(i).getGuestName());
                    jObjectData.put("email", GuestList.get(i).getGuestEmailId());
                    jObjectData.put("mobile", GuestList.get(i).getGuestMobile());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(jObjectData);
                //  int position = GuestList.size();
              /*  GuestList.remove(position - 1);
                AddGuestModel model = new AddGuestModel(name, EmailId, MobileNo);
                GuestList.add(model);*/

            }
            JSONObject finalobject = new JSONObject();
            try {
                finalobject.put("GuestList", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            guestJson = finalobject.toString();
        }


        String emplSelect = "";

        if (selected.endsWith(",")) {

            emplSelect = selected.substring(0, selected.length() - 1);
        }
        //   Log.e("selected", emplSelect);


        bookseatAPI(BayId, workspaceId, GuestList.size(), edtMeetingAganda.getText().toString(), emplSelect, guestJson);

    }

    private void bookseatAPI(String bayId, String workspaceId, int size, String toString, String emplSelect, String guestJson) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        //    jsonParams.put("id", workspaceId);
        jsonParams.put("type", "meeting");
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("bay_id", bayId);
        jsonParams.put("guest_count", size + "");
        jsonParams.put("meeting_topic", toString);
        jsonParams.put("guest_info", guestJson);
        jsonParams.put("employees", emplSelect);
        jsonParams.put("start_datetime", selectedDate + " " + sendTime);
        jsonParams.put("end_datetime", selectedDate + " " + sendTime);

        String json = jsonParams.toString();
        //  Log.e("json", json);

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
                            intent.putExtra("startDate", selectedDate + " " + sendTime);
                            intent.putExtra("endDate", selectedDate + " " + sendTime);
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

    private void customSelectEmployee() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dailog_mult_select_employee);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.employeeList);


        EditText searchEdit = (EditText) dialog.findViewById(R.id.edtSearch);
        TextView txtclearAll = (TextView) dialog.findViewById(R.id.txtCleaAll) ;
        TextView txtCancel = (TextView) dialog.findViewById(R.id.txtCancel) ;
        TextView txtAdd = (TextView) dialog.findViewById(R.id.txtAdd) ;

        dialog.show();



    }

    private void selectEmployee() {

        checkedItems = new boolean[listofEmployee.size()];


          mStringArray = listofEmployee.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkspaceDetail.this);

        // set the title for the alert dialog
        builder.setTitle("Select Employee");

        // set the icon for the alert dialog
        builder.setIcon(R.drawable.logo);


        // now this is the function which sets the alert dialog for multiple item selection ready
        builder.setMultiChoiceItems(mStringArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {



             /*       selectedItems.add(mStringArray[which]);

                }else if(selectedItems.contains(mStringArray[which])) {
                    selectedItems.remove(mStringArray[which]);
                } */
                  if(isChecked) {
                    if(! mUserItems.contains(which)){
                        mUserItems.add(which);
                     //   checkedItems[which] = isChecked;
                    }
                }else if(mUserItems.contains(which)){
                    mUserItems.remove(which);
                }



              //  String currentItem = selectedItems.get(which);
            }
        });

        // alert dialog shouldn't be cancellable
        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String item = "";
                for (int i =0; i<mUserItems.size(); i++ ){
                    item = item+ mStringArray[mUserItems.get(i)];

                    if(i != mUserItems.size() - 1){
                        item = item + ",";
                    }
                }


               // txtProgressAddEmployee.setText(mUserItems.size() + " Participants");
               /* selectedItems = selectedEmplyee;
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        Log.e("checkedItem", checkedItems.length + "");
                        //  selectedEmplyee.add(selectedItems.get(i));
                        SelectEmployees.add(Employees.get(i));
                     //   selectedEmplyee.add(selectedItems.get(i));
                        Log.e("posion", i + "");
                        // tvSelectedItemsPreview.setText(tvSelectedItemsPreview.getText() + selectedItems.get(i) + ", ");

                    }

                }

                for (int i = 0; i < SelectEmployees.size(); i++) {


                    // tvSelectedItemsPreview.setText(tvSelectedItemsPreview.getText() + selectedItems.get(i) + ", ");
                    if (i == 0) {
                        selected = SelectEmployees.get(i).getId() + ",";
                    } else {
                        selected = selected + SelectEmployees.get(i).getId() + ",";
                    }
                }

*/
                if (mUserItems.size() >= 1) {
                    txtProgressAddEmployee.setText(mUserItems.size() + " Participants");
                    //   TotalSelectedEmp.setText(SelectEmployees.size() + " Participants");
                    //    prograssAddEmployee.setVisibility(View.VISIBLE);
                    showView(prograssAddEmployee);

                } else {
                    //   prograssAddEmployee.setVisibility(View.INVISIBLE);
                    hideView(prograssAddEmployee);
                }


            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onItemClick(View v, int position) {

        if (v.getId() == R.id.Delete) {
            GuestList.remove(position);
            guestAdator.notifyDataSetChanged();


        }

        if (v.getId() == R.id.Deleteimage) {
            //  selectedEmplyee.remove(position);
            SelectEmployees.remove(position);
            seletedEmplyee.notifyDataSetChanged();


        }


    }

    private void showView(View view) {
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        //toggling visibility
        view.setVisibility(View.VISIBLE);

        //adding sliding effect
        view.startAnimation(slideDown);

    }

    private void hideView(View view) {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);


        //toggling visibility
        view.setVisibility(View.GONE);

        //adding sliding effect
        view.startAnimation(slideUp);
    }


}
