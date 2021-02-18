package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccefullyDeskBook extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.BookingNumber)
    TextView BookingNumber;
    @BindView(R.id.dateDetail)
    TextView dateDetail;
    @BindView(R.id.DeskDetail)
    TextView DeskDetail;
    String bookingNumber, startDate, endDate, workspaceName, workspaceAddress, seatId;
    @BindView(R.id.WorkSpaceName)
    TextView WorkSpaceName;
    @BindView(R.id.deskWorkspaceAddress)
    TextView deskWorkspaceAddress;
    @BindView(R.id.btnDone)
    TextView btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succefully_desk_book);
        ButterKnife.bind(this);

        btnDone.setOnClickListener(this);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        bookingNumber = getIntent().getStringExtra("bookingNumber");
        workspaceName = getIntent().getStringExtra("workspaceName");
        workspaceAddress = getIntent().getStringExtra("workspaceAddress");
        seatId = getIntent().getStringExtra("seatId");

        DeskDetail.setText("Desk " + seatId);
        dateDetail.setText(startDate + " to " + endDate);
        BookingNumber.setText("Booking Number is " + bookingNumber);
        WorkSpaceName.setText(workspaceName);
        deskWorkspaceAddress.setText(workspaceAddress);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom);
        btnDone.startAnimation(animation1);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnDone:


                Intent intent = new Intent(SuccefullyDeskBook.this, MainActivity.class);
                startActivity(intent);

                break;

        }

    }
}