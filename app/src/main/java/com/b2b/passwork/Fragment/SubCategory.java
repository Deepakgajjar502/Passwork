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
import com.b2b.passwork.Adaptor.subCategoryAdaptor;
import com.b2b.passwork.Model.Category.CategoryModel;
import com.b2b.passwork.Model.Category.CategoryResponse;
import com.b2b.passwork.Model.SubCategory.SubCategoryModel;
import com.b2b.passwork.Model.SubCategory.SubCateogryResponse;
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


public class SubCategory extends Fragment implements View.OnClickListener, fragment_position , OnItemClickListener {


    @BindView(R.id.serveryCategory)
    RecyclerView serveryCategory;
    ArrayList<SubCategoryModel> categoryList = new ArrayList();
    subCategoryAdaptor adaptor;
    @BindView(R.id.btnNext)
    Button btnNext;

    fragment_position position;
    String CategoryId, Token, CorporateId;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    UserSessionManager session;
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

        session = new UserSessionManager(getActivity());


        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        Bundle arguments = getArguments();
        CategoryId = arguments.getString("categoryId");

        screenOpen(2);
        getSubCategory(CategoryId);


        Log.e("CategoryId",CategoryId );


        return view;
    }

    private void getSubCategory(String categoryId) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("corporate_id", CorporateId);
        jsonParams.put("category_id", categoryId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SubCateogryResponse> responseBody = RestManager.getInstance().getService()
                .getSubCatoegry(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SubCateogryResponse>() {
            @Override
            public void onResponse(Call<SubCateogryResponse> call, Response<SubCateogryResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        SubCateogryResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            categoryList = response1.getData();

                            adaptor = new subCategoryAdaptor(getActivity(), categoryList, btnNext);
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            serveryCategory.setLayoutManager(horizontaLayoutManagaer);
                            adaptor.setOnItemClickListener(SubCategory.this::onItemClick);
                            serveryCategory.setAdapter(adaptor);




                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SubCateogryResponse> call, Throwable t) {
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
                    //   Toast.makeText(getActivity(), adaptor.getSelect().getName(), Toast.LENGTH_SHORT).show();
                  //  loadFragment(new WhenandWhere());

                    WhenandWhere fragment = new WhenandWhere();
                    Bundle arguments = new Bundle();
                    arguments.putString( "categoryId" , CategoryId);
                    arguments.putString( "subCategory" , adaptor.getSelect().getId());
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

    private void loadFragment(WhenandWhere subCategory) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
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

    @Override
    public void onItemClick(View v, int position) {

    }
}