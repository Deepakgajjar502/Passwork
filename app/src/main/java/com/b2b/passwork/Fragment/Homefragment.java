package com.b2b.passwork.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Adaptor.WorkSpaceListAdaptor;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.LinePagerIndicatorDecoration;
import com.b2b.passwork.Utility.PicassoHelper;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.interfaces.workspace_interface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;


public class Homefragment extends Fragment  {


    @BindView(R.id.UserName)
    TextView UserName;
    @BindView(R.id.Profile)
    ImageView profile;



    String[] OfficeName = new String[]{"CFE Offices - Business Center", "WorkWise Solutions Pvt. Ltd", "Rise India", "Bloomdesk Coworking Space", "Millennial Pod", "Spartan Co-Work"};
    String[] OfficeAddress = new String[]{"Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory,Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072", "Near Chandivali Ice Factory, Mumbai,\nPincode :400072"};
    Integer[] OfficeImage = new Integer[]{R.drawable.office_a, R.drawable.office_b, R.drawable.office_c, R.drawable.office_d, R.drawable.office_e, R.drawable.office_f};
    String[] SheduleOfficeName = new String[]{"Millennial Pod", "Spartan Co-Work"};
    Integer[] SheduleOfficeImage = new Integer[]{R.drawable.office_e, R.drawable.office_f};
    String[] SheduleDate = new String[]{"25-11-2021", "27-11-2021"};
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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        ButterKnife.bind(this, view);


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



        return view;
    }





}