package com.b2b.passwork.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.fragment_position;
import com.b2b.passwork.retrofit.RestManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.DateFormat;
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

public class WhenandWhere extends Fragment implements View.OnClickListener , fragment_position {
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtMonthYear)
    TextView txtMonthYear;
    @BindView(R.id.txtDay)
    TextView txtDay;
    @BindView(R.id.DateLayout)
    LinearLayout DateLayout;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.TimeLayout)
    LinearLayout TimeLayout;
    @BindView(R.id.dateAndTimeLayout)
    LinearLayout dateLayout;
    @BindView(R.id.edt_Site)
    EditText edtSite;
    @BindView(R.id.SiteLayout)
    TextInputLayout SiteLayout;
    @BindView(R.id.edt_workSpace)
    EditText edtWorkSpace;
    @BindView(R.id.WorkSpaceLayout)
    TextInputLayout WorkSpaceLayout;
    @BindView(R.id.edt_select_floor)
    EditText edtSelectFloor;
    @BindView(R.id.FloorLayout)
    TextInputLayout FloorLayout;
    List<FloorsItem> floors;
    UserSessionManager session;
    String WorkspaceId, Token, workspaceName, CorporateId, CorporateName;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    ArrayList<String> listofFloorName = new ArrayList<>();
    Boolean btnEnable = false;
    @BindView(R.id.btnNext)
    Button btnNext;
    Calendar myCalendar;
    fragment_position position;
    DatePickerDialog.OnDateSetListener date;
    String CategoryId, subCategoryID ,  fullDate, FloorId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            position = (fragment_position) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_whenand_where, container, false);
        ButterKnife.bind(this, view);
        screenOpen(3);

        Bundle arguments = getArguments();
        CategoryId = arguments.getString("categoryId");
        subCategoryID = arguments.getString("subCategory");

        DateLayout.setOnClickListener(this);
        TimeLayout.setOnClickListener(this);
        session = new UserSessionManager(getActivity());
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);

        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        CorporateName = user.get(UserSessionManager.KEY_COMPANY_NAME);
        edtSelectFloor.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        edtSite.setText(CorporateName);
        edtWorkSpace.setText(workspaceName);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        String EndDate = dateFormat.format(tomorrow);
        String StartDate = dateFormat.format(tomorrow);


        String Date = "dd"; //In which you need put here
        SimpleDateFormat Datesdf = new SimpleDateFormat(Date, Locale.US);
        String Day = "EEE"; //In which you need put here
        SimpleDateFormat Daysdf = new SimpleDateFormat(Day, Locale.US);
        String monthYear = "MMM yyyy"; //In which you need put here
        SimpleDateFormat monthYearsdf = new SimpleDateFormat(monthYear, Locale.US);

        String fulldateformate = "yyyy-MM-dd";
        SimpleDateFormat fulldatesdf = new SimpleDateFormat(fulldateformate, Locale.US);
        fullDate = fulldatesdf.format(calendar.getTime());

        txtDate.setText(Datesdf.format(calendar.getTime()));
        txtDay.setText(Daysdf.format(calendar.getTime()));
        txtMonthYear.setText(monthYearsdf.format(calendar.getTime()));



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

        getFloorDetail(WorkspaceId, StartDate, EndDate);


        return view;
    }

    private void updateLabel() {

        String Date = "dd"; //In which you need put here
        SimpleDateFormat Datesdf = new SimpleDateFormat(Date, Locale.US);
        String Day = "EEE"; //In which you need put here
        SimpleDateFormat Daysdf = new SimpleDateFormat(Day, Locale.US);
        String monthYear = "MMM yyyy"; //In which you need put here
        SimpleDateFormat monthYearsdf = new SimpleDateFormat(monthYear, Locale.US);
        String fulldateformate = "yyyy-MM-dd";
        SimpleDateFormat fulldatesdf = new SimpleDateFormat(fulldateformate, Locale.US);
         fullDate = fulldatesdf.format(myCalendar.getTime());

        txtDate.setText(Datesdf.format(myCalendar.getTime()));
        txtDay.setText(Daysdf.format(myCalendar.getTime()));
        txtMonthYear.setText(monthYearsdf.format(myCalendar.getTime()));

    }

    private void getFloorDetail(String workspaceId, String startDate, String endDate) {

        progressBar.setVisibility(View.VISIBLE);

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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.edt_select_floor:


                selectFloorDailog();

                break;

            case R.id.btnNext:

                if(btnEnable) {

                    ServiceAdditional fragment = new ServiceAdditional();
                    Bundle arguments = new Bundle();
                    arguments.putString( "categoryId" , CategoryId);
                    arguments.putString( "subCategory" , subCategoryID);
                    arguments.putString( "time" , txtTime.getText().toString().trim());
                    arguments.putString( "date" , fullDate);
                    arguments.putString( "floorId" , FloorId);
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("null")
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();


                    //   loadFragment(new ServiceAdditional());
                }

                break;

            case R.id.DateLayout:

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.TimeLayout:

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();





                break;

        }
    }

    private void selectFloorDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Floor");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice, listofFloorName);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSelectFloor.setText(listofFloorName.get(which));
                FloorId = floors.get(which).getFloorId()+"";
                // Toast.makeText(WorkspaceLayout.this, floors.get(which).getFloorName(), Toast.LENGTH_SHORT).show();
                btnEnable = true;
                btnNext.setBackgroundColor(getResources().getColor(R.color.accent));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void loadFragment(ServiceAdditional subCategory) {
        FragmentTransaction transaction =    getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.fragmentContainer, subCategory);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void screenOpen(int screenPossion) {
        position.screenOpen(screenPossion);
    }
}