package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.SelectBookingSpace;
import com.b2b.passwork.Adaptor.BayAdaptor;
import com.b2b.passwork.Adaptor.InvetoryAdatpor;
import com.b2b.passwork.Model.Room.GetBay.InventoryItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewInventoryItem;
import com.b2b.passwork.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InvontoryFramgment extends Fragment {


    ArrayList<NewInventoryItem> inventory;
    ArrayList<NewInventoryItem> workSpalceList = new ArrayList<>();
    @BindView(R.id.inventory)
    RecyclerView Recyinventory;
    InvetoryAdatpor adaptor;
    String foorName, selectedDate, baySelectedDate, showDate;
    Boolean selectype;

    public static InvontoryFramgment newInstance(ArrayList<NewInventoryItem> inventory, String floorName, String selectedDate, Boolean selectType, String baySelectedDate, String showDate) {
        InvontoryFramgment fragment = new InvontoryFramgment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("invList", inventory);
        bundle.putSerializable("floorName", floorName);
        bundle.putSerializable("selectedDate", selectedDate);
        bundle.putBoolean("selectype", selectType);
        bundle.putSerializable("baySelectedDate", baySelectedDate);
        bundle.putSerializable("showDate", showDate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invontory_framgment, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            Bundle arguments = getArguments();
            inventory = (ArrayList<NewInventoryItem>) arguments.getSerializable("invList");
            foorName = arguments.getString("floorName");
            selectedDate = arguments.getString("selectedDate");
            selectype =  arguments.getBoolean("selectype", true);
            baySelectedDate = arguments.getString("baySelectedDate");
            showDate = arguments.getString("showDate");
        }




        for (int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i).getBayType().equals("Open Work")){
                workSpalceList.add(inventory.get(i));
            }
            //do something with object
        }

        adaptor = new InvetoryAdatpor(getActivity(), workSpalceList, foorName, selectedDate, selectype, baySelectedDate, showDate);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Recyinventory.setLayoutManager(horizontaLayoutManagaer);
        //adaptor.setOnItemClickListener(SelectBookingSpace.this);
        Recyinventory.setAdapter(adaptor);



        return view;
    }
}