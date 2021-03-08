package com.b2b.passwork.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.Service_Request;
import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.Adaptor.CateogryAdaptor;
import com.b2b.passwork.Adaptor.Poll_Adaptor;
import com.b2b.passwork.Model.CategoryModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServiceCategory extends Fragment  implements View.OnClickListener, OnItemClickListener {


    @BindView(R.id.serveryCategory)
    RecyclerView serveryCategory;
    ArrayList<CategoryModel> categoryList = new ArrayList();
    CateogryAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_category, container, false);
        ButterKnife.bind(this, view);

        CategoryModel model = new CategoryModel("1", "Access Control System");
        categoryList.add(model);
        model = new CategoryModel("2", "Air-Conditioning");
        categoryList.add(model);
        model = new CategoryModel("3", "Cleaning");
        categoryList.add(model);
        model = new CategoryModel("4", "Electrical");
        categoryList.add(model);
        model = new CategoryModel("5", "Hardware");
        categoryList.add(model);
        model = new CategoryModel("6", "IT");
        categoryList.add(model);
        model = new CategoryModel("7", "Manpower Service");
        categoryList.add(model);
        model = new CategoryModel("8", "Pantry");
        categoryList.add(model);
        model = new CategoryModel("9", "Pest Control");
        categoryList.add(model);
        model = new CategoryModel("10", "Pest Control");
        categoryList.add(model);
        model = new CategoryModel("11", "Plumbing");
        categoryList.add(model);
        model = new CategoryModel("12", "Stationary");
        categoryList.add(model);


        adaptor = new CateogryAdaptor(getActivity(), categoryList);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        serveryCategory.setLayoutManager(horizontaLayoutManagaer);
        adaptor.setOnItemClickListener(ServiceCategory.this::onItemClick);
        serveryCategory.setAdapter(adaptor);


        return view;
    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void onItemClick(View view, int position) {

        if (view.getId() == R.id.txtCageogry) {

            Log.e("click", "working");
           TextView txtCagetory =   view.findViewById(R.id.txtCageogry);
           txtCagetory.setBackgroundResource(R.drawable.bg_category);


        }


    }

}