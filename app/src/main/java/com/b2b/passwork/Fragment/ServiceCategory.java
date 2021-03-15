package com.b2b.passwork.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.CateogryAdaptor;
import com.b2b.passwork.Model.Category.CategoryModel;
import com.b2b.passwork.Model.Category.CategoryResponse;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.interfaces.fragment_position;
import com.b2b.passwork.retrofit.RestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceCategory extends Fragment implements View.OnClickListener, OnItemClickListener, fragment_position {


    @BindView(R.id.serveryCategory)
    RecyclerView serveryCategory;
    ArrayList<CategoryModel> categoryList = new ArrayList();
    CateogryAdaptor adaptor;
    @BindView(R.id.btnNext)
    Button btnNext;
    fragment_position position;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    UserSessionManager session;
    String Token, CorporateId;
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

        session = new UserSessionManager(getActivity());


        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        getCategory();




        return view;
    }

    private void getCategory() {


        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("corporate_id", CorporateId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<CategoryResponse> responseBody = RestManager.getInstance().getService()
                .getCatoegry(token, body);

        //"artist",
        responseBody.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        CategoryResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            categoryList = response1.getCategory();

                            adaptor = new CateogryAdaptor(getActivity(), categoryList, btnNext);
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            serveryCategory.setLayoutManager(horizontaLayoutManagaer);
                            adaptor.setOnItemClickListener(ServiceCategory.this::onItemClick);
                            serveryCategory.setAdapter(adaptor);




                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
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

            case R.id.btnNext:

                if (adaptor.getSelect() != null) {
                    //  Toast.makeText(getActivity(), adaptor.getSelect().getCategoryTitle(), Toast.LENGTH_SHORT).show();
                 //   loadFragment(new SubCategory());

                    SubCategory fragment = new SubCategory();
                    Bundle arguments = new Bundle();
                    arguments.putString( "categoryId" , adaptor.getSelect().getId());
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("null")
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();


                } else {
                    Toast.makeText(getActivity(), "Please slect Category", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    private void loadFragment(SubCategory subCategory) {




        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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

