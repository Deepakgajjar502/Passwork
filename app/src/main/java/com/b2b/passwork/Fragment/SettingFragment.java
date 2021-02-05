package com.b2b.passwork.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Activity.LoginActivity;
import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.Model.ProfileResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.retrofit.RestManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingFragment extends Fragment {


    @BindView(R.id.imageLogo)
    ImageView imageLogo;
    @BindView(R.id.imageAvatar)
    ImageView imageAvatar;
    @BindView(R.id.layout_Image)
    RelativeLayout layoutImage;
    @BindView(R.id.UserName)
    TextView User_Name;
    @BindView(R.id.companyName)
    TextView companyName;
    @BindView(R.id.companyCode)
    TextView companyCode;
    @BindView(R.id.followerLayout)
    LinearLayout followerLayout;
    @BindView(R.id.layout_profile)
    RelativeLayout layoutProfile;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.parent)
    CoordinatorLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);


        getProfileAPI();


        // Inflate the layout for this fragment
        return view;
    }

    private void getProfileAPI() {


        progressBar.setVisibility(View.VISIBLE);


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", "1");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiNzE5MjgyOWQ0OWFlYjExNTliN2YwNGUxNGE5OGE2OGViMjk1NTU4ZTgzMjA4NDMxMzYwZmRiOGFmMmFjNWEyNyIsInV1aWQiOiIwYjcwMmQ1Zi05MGI0LTQ2YWUtYjQ4Yy1kOGVjM2RiMTMxZmEiLCJpYXQiOjE2MTI1Mjk2MjQsImV4cCI6MTYxNTEyMTYyNH0.ZpCC0Xp3QXgoevs5HS3plM4RdBWzh8bW_Erm9BOpPow";

        Call<ProfileResponse> responseBody = RestManager.getInstance().getService()
                .getProfile(token, body);

        //"artist",
        responseBody.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        ProfileResponse response1 = response.body();

                        User_Name.setText(response1.getFirstName()+" "+ response1.getLastName());
                        companyName.setText(response1.getCorporateName());
                        companyCode.setText(response1.getRole());




                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }
}