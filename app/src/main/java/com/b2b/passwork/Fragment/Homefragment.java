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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Adaptor.WorkSpaceListAdaptor;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.LinePagerIndicatorDecoration;
import com.b2b.passwork.Utility.PicassoHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Homefragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.UserName)
    TextView UserName;
    @BindView(R.id.Profile)
    ImageView profile;


    String[] OfficeName = new String[]{"CFE Offices - Business Center", "WorkWise Solutions Pvt. Ltd", "Rise India", "Bloomdesk Coworking Space", "Millennial Pod", "Spartan Co-Work"};
    String[] OfficeAddress = new String[]{"Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory,Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072"};
    Integer[] OfficeImage = new Integer[]{R.drawable.office_a, R.drawable.office_b, R.drawable.office_c, R.drawable.office_d, R.drawable.office_e, R.drawable.office_f};
    String[] SheduleOfficeName = new String[]{"Millennial Pod", "Spartan Co-Work"};
    Integer[] SheduleOfficeImage = new Integer[]{R.drawable.office_e, R.drawable.office_f};
    String[] SheduleDate = new String[]{"02-02-2021", "05-02-2021"};
    String[] TypeofBook = new String[]{"Personal", "Group Meeting"};

    WorkSpaceListAdaptor Adaptor;

    @BindView(R.id.ListofShedule)
    RecyclerView ListWorkShedule;
    SheduleAdaptor sheduleAdaptor;
    @BindView(R.id.header)
    LinearLayout header;

    @BindView(R.id.round)
    TextView round;
    @BindView(R.id.ListWorkSpace)
    ViewPager2 ListWorkSpace;
    @BindView(R.id.workspace)
    LinearLayout workspace;
    @BindView(R.id.selectDate)
    TextInputLayout selectDate;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    @BindView(R.id.sheducl)
    TextView sheducl;
    @BindView(R.id.edt_select_workspaceDate)
    EditText edtSelectWorkspace;
    @BindView(R.id.workspaceDetail)
    LinearLayout workspaceDetail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        ButterKnife.bind(this, view);

        edtSelectWorkspace.setOnClickListener(this);
        workspace.setOnClickListener(this);
        Picasso.get().load(R.drawable.progile)
                .transform(new PicassoHelper.PicassoCircleTransformation())
                .into(profile);

        Adaptor = new WorkSpaceListAdaptor(getActivity(), OfficeName, OfficeImage, OfficeAddress);
        ListWorkSpace.setAdapter(Adaptor);

        ListWorkSpace.setClipToPadding(false);
        ListWorkSpace.setClipChildren(false);
        ListWorkSpace.setOffscreenPageLimit(3);
        ListWorkSpace.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        ListWorkSpace.setCurrentItem(1);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        ListWorkSpace.setPageTransformer(compositePageTransformer);


        sheduleAdaptor = new SheduleAdaptor(getActivity(), SheduleOfficeName, SheduleOfficeImage, SheduleDate, TypeofBook);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ListWorkShedule.setLayoutManager(horizontaLayoutManagaer);
        ListWorkShedule.setAdapter(sheduleAdaptor);
        ListWorkShedule.addItemDecoration(new LinePagerIndicatorDecoration());
        horizontaLayoutManagaer.findFirstVisibleItemPosition();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };


        return view;
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.edt_select_workspaceDate:
                Log.e("click", "working");
                new DatePickerDialog(getActivity(), R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                //do your stuff
                break;

            case R.id.workspace:
                Log.e("click", "working");
                Intent intent = new Intent(getContext(), WorkspaceLayout.class);
                startActivity(intent);


                //do your stuff
                break;

        }

    }
}