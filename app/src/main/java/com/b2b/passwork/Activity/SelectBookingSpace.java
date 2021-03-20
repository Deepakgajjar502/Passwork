package com.b2b.passwork.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.b2b.passwork.R;
import com.b2b.passwork.Utility.UserSessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SelectBookingSpace extends AppCompatActivity {

    String selectedDate, Token, WorkspaceId ;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_booking_space);


        selectedDate = getIntent().getStringExtra("date");
        Log.e("selectedDate",selectedDate );


        session = new UserSessionManager(this);
        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);


        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);

        Calendar calendar = Calendar.getInstance();


        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        String  EndDate = dateFormat.format(tomorrow);
        String  StartDate = dateFormat.format(tomorrow);


      //  getFloorDetail(WorkspaceId, StartDate, EndDate );
    }
}