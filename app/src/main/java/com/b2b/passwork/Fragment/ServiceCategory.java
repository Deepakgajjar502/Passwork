package com.b2b.passwork.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.CateogryAdaptor;
import com.b2b.passwork.Adaptor.subCategoryAdaptor;
import com.b2b.passwork.Model.CategoryModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.interfaces.fragment_position;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServiceCategory extends Fragment implements View.OnClickListener, OnItemClickListener, fragment_position {


    @BindView(R.id.serveryCategory)
    RecyclerView serveryCategory;
    ArrayList<CategoryModel> categoryList = new ArrayList();
    subCategoryAdaptor adaptor;
    @BindView(R.id.btnNext)
    Button btnNext;
    fragment_position position;

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
        View view = inflater.inflate(R.layout.fragment_service_category, container, false);
        ButterKnife.bind(this, view);
        categoryList.clear();
        btnNext.setOnClickListener(this);
        screenOpen(1);
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
        model = new CategoryModel("10", "Plumbing");
        categoryList.add(model);
        model = new CategoryModel("11", "Stationary");
        categoryList.add(model);



        adaptor = new subCategoryAdaptor(getActivity(), categoryList,  btnNext);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        serveryCategory.setLayoutManager(horizontaLayoutManagaer);
        adaptor.setOnItemClickListener(ServiceCategory.this::onItemClick);
        serveryCategory.setAdapter(adaptor);


        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:

                if(adaptor.getSelect() !=null){
                  //  Toast.makeText(getActivity(), adaptor.getSelect().getCategoryTitle(), Toast.LENGTH_SHORT).show();
                    loadFragment(new SubCategory());


                }else {
                    Toast.makeText(getActivity(), "Please slect Category", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    private void loadFragment(SubCategory subCategory) {
        FragmentTransaction transaction =    getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.fragmentContainer, subCategory);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void onItemClick(View view, int position) {



    }


    @Override
    public void screenOpen(int screenPossion) {

        position.screenOpen(screenPossion);


    }
}

