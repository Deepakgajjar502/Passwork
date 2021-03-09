package com.b2b.passwork.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.b2b.passwork.Adaptor.CateogryAdaptor;
import com.b2b.passwork.Model.CategoryModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.fragment_position;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubCategory extends Fragment implements View.OnClickListener, fragment_position {


    @BindView(R.id.serveryCategory)
    RecyclerView serveryCategory;
    ArrayList<CategoryModel> categoryList = new ArrayList();
    CateogryAdaptor adaptor;
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

        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, view);
        categoryList.clear();
        btnNext.setOnClickListener(this);

        screenOpen(2);
        CategoryModel model = new CategoryModel("1", "Floor is wet/slippery/ dirty");
        categoryList.add(model);
        model = new CategoryModel("2", "Drain/ Washbasin choke");
        categoryList.add(model);
        model = new CategoryModel("3", "Urinal Pot to be cleaned");
        categoryList.add(model);
        model = new CategoryModel("4", "WE- not clean");
        categoryList.add(model);
        model = new CategoryModel("5", "Coffee spillage");
        categoryList.add(model);
        model = new CategoryModel("6", "Soap empty in soap dispenser");
        categoryList.add(model);
        model = new CategoryModel("7", "Foul smell");
        categoryList.add(model);
        model = new CategoryModel("8", "Transh Bin not cleared");
        categoryList.add(model);




        adaptor = new CateogryAdaptor(getActivity(), categoryList,  btnNext);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        serveryCategory.setLayoutManager(horizontaLayoutManagaer);
        serveryCategory.setAdapter(adaptor);






        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:

                if(adaptor.getSelect() !=null){
                    Toast.makeText(getActivity(), adaptor.getSelect().getCategoryTitle(), Toast.LENGTH_SHORT).show();
                    loadFragment(new WhenandWhere());

                }else {
                    Toast.makeText(getActivity(), "Please slect Category", Toast.LENGTH_SHORT).show();
                }

                break;


        }

    }

    private void loadFragment(WhenandWhere subCategory) {
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