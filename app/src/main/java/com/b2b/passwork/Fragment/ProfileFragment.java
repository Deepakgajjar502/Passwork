package com.b2b.passwork.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Activity.LoginActivity;
import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.Login.LoginResponse;
import com.b2b.passwork.Model.ProfileResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.Image)
    ImageView Image;
    @BindView(R.id.titlepage)
    TextView titlepage;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.IconFirstName)
    ImageView IconFirstName;
    @BindView(R.id.FirstNameEditText)
    EditText FirstNameEditText;
    @BindView(R.id.LayoutFullName)
    LinearLayout LayoutFullName;
    @BindView(R.id.IconLastName)
    ImageView IconLastName;
    @BindView(R.id.LastNameEditText)
    EditText LastNameEditText;
    @BindView(R.id.LayoutAbout)
    LinearLayout LayoutAbout;
    @BindView(R.id.IconEmail)
    ImageView IconEmail;
    @BindView(R.id.MobileEditText)
    EditText mobileEditText;
    @BindView(R.id.LayoutFacebook)
    LinearLayout LayoutFacebook;
    @BindView(R.id.ApplyButton)
    Button ApplyButton;
    @BindView(R.id.lyt_form)
    LinearLayout lytForm;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    UserSessionManager session;
    String UserId;
    String Token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        session = new UserSessionManager(getActivity());
        Image.setOnClickListener(this);
        ApplyButton.setOnClickListener(this);

        HashMap<String, String> user = session.getUserDetails();
        String firstName = user.get(UserSessionManager.KEY_FIRST_NAME);
        String LastName = user.get(UserSessionManager.KEY_LAST_NAME);
        String Mobile = user.get(UserSessionManager.KEY_MOBILE);
        UserId = user.get(UserSessionManager.KEY_ID);
        Token =  user.get(UserSessionManager.KEY_ACCESS_TOKEN);


        FirstNameEditText.setText(firstName );
        LastNameEditText.setText(LastName );
        mobileEditText.setText(Mobile);





        return view;


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.Image:

                loadFragment(new SettingFragment());
                //do your stuff
                break;



            case R.id.ApplyButton:
                Log.e("click", "working");

                if (validated()) {

                    updateProfile();
                }





                //do your stuff
                break;


        }


    }


    private boolean validated() {

        if (FirstNameEditText.getText().toString().trim().equals("")) {
            FirstNameEditText.setError("This field can not be blank.");
            FirstNameEditText.requestFocus();
            return false;
        }

        if (LastNameEditText.getText().toString().trim().equals("")) {
            LastNameEditText.setError("This field can not be blank.");
            LastNameEditText.requestFocus();
            return false;
        }
        if (mobileEditText.getText().toString().trim().equals("")) {
            mobileEditText.setError("This field can not be blank.");
            mobileEditText.requestFocus();
            return false;
        }





        return true;

    }



    private void updateProfile() {



        progressbar.setVisibility(View.VISIBLE);

        String token = "Bearer " + Token;
        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("first_name", FirstNameEditText.getText().toString());
        jsonParams.put("last_name", LastNameEditText.getText().toString());
        jsonParams.put("mobile", mobileEditText.getText().toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<DefaultResponse> responseBody = RestManager.getInstance().getService()
                .getUpdateProfile(token, body);
        //"artist",
        responseBody.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response ) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressbar.setVisibility(View.GONE);

                    if (response.body() != null) {

                        if (response.isSuccessful()) {

                            DefaultResponse   response1 = response.body();

                            if (response1.getStatus()==1) {


                                StaticUtil.showToast(getActivity(), "Profile update successfully");


                                session.setUpdateProfile( FirstNameEditText.getText().toString(),  LastNameEditText.getText().toString(),  mobileEditText.getText().toString());

                                loadFragment(new SettingFragment());



                            } else {

                                StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");
                                Log.e("status.equal", "Something went wrong");
                            }

                        } else {


                            StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");
                            Log.e("response.isSuccessful()", "Something went wrong");

                        }

                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");
                        Log.e("response.body() != null", "Something went wrong");
                    }


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressbar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");

                Log.e("error Throwable", t.getMessage().toString());
            }
        });







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