package com.b2b.passwork.Fragment;

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
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Model.ProfileResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingFragment extends Fragment implements View.OnClickListener {


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
    UserSessionManager session;
    String UserId;
    @BindView(R.id.LogoutLayout)
    LinearLayout LogoutLayout;
    @BindView(R.id.editProfileLayout)
    LinearLayout editProfileLayout;
    String Token;
    @BindView(R.id.changePasswordLayout)
    LinearLayout changePasswordLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        String firstName = user.get(UserSessionManager.KEY_FIRST_NAME);
        String LastName = user.get(UserSessionManager.KEY_LAST_NAME);
        UserId = user.get(UserSessionManager.KEY_ID);
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);

        User_Name.setText(firstName + " " + LastName);
        companyName.setText(user.get(UserSessionManager.KEY_COMPANY_NAME));
        companyCode.setText(user.get(UserSessionManager.KEY_ROLE));

        LogoutLayout.setOnClickListener(this);
        editProfileLayout.setOnClickListener(this);
        changePasswordLayout.setOnClickListener(this);


        getProfileAPI();


        // Inflate the layout for this fragment
        return view;
    }

    private void getProfileAPI() {


        progressBar.setVisibility(View.VISIBLE);


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", UserId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

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

                        User_Name.setText(response1.getFirstName() + " " + response1.getLastName());
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

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.LogoutLayout:

                final iOSDialog iOSDialog = new iOSDialog(getActivity());
                iOSDialog.setTitle(getActivity().getString(R.string.app_name));
                iOSDialog.setSubtitle(getActivity().getString(R.string.Lbl_Are_you_logout));
                iOSDialog.setPositiveLabel(getActivity().getString(R.string.Lbl_Logout));
                iOSDialog.setNegativeLabel(getActivity().getString(R.string.Lbl_Cancel));
                iOSDialog.setBoldPositiveLabel(true);
                iOSDialog.setNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.setPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                        session.logoutUser();
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.show();


                break;

            case R.id.editProfileLayout:

                loadFragment(new ProfileFragment());


                break;

            case R.id.changePasswordLayout:

                loadFragment(new ChangePassword());


                break;

        }

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("null")
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;

    }
}