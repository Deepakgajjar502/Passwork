package com.b2b.passwork.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.R;
import com.ebanx.swipebtn.SwipeButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import noman.weekcalendar.WeekCalendar;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_meeting, container, false);
        ButterKnife.bind(this, view);





        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {



        }
    }
}