package com.b2b.passwork.Fragment;

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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.gdacciaro.iOSDialog.iOSDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePassword extends Fragment implements View.OnClickListener {

    @BindView(R.id.Image)
    ImageView Image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.OldPasswordEditText)
    EditText OldPasswordEditText;
    @BindView(R.id.LayoutOldName)
    LinearLayout LayoutOldName;
    @BindView(R.id.NewEditText)
    EditText NewEditText;
    @BindView(R.id.LayoutNewPassword)
    LinearLayout LayoutNewPassword;
    @BindView(R.id.confirmEditText)
    EditText confirmEditText;
    @BindView(R.id.LayoutConfirmPassword)
    LinearLayout LayoutConfirmPassword;
    @BindView(R.id.ApplyButton)
    Button ApplyButton;
    @BindView(R.id.lyt_form)
    LinearLayout lytForm;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    UserSessionManager session;
    String UserId;
    String Token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        session = new UserSessionManager(getActivity());

        Image.setOnClickListener(this);
        ApplyButton.setOnClickListener(this);
        HashMap<String, String> user = session.getUserDetails();

        Token =  user.get(UserSessionManager.KEY_ACCESS_TOKEN);
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

                    updatePassword();
                }

             //do your stuff
                break;


        }



    }

    private void updatePassword() {



        progressBar.setVisibility(View.VISIBLE);

        String token = "Bearer " + Token;
        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("old_password", OldPasswordEditText.getText().toString());
        jsonParams.put("new_password", NewEditText.getText().toString());
        jsonParams.put("confirm_password", confirmEditText.getText().toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<DefaultResponse> responseBody = RestManager.getInstance().getService()
                .getUpdatepassword(token, body);
        //"artist",
        responseBody.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response ) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);

                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        DefaultResponse   response1 = response.body();

                        if (response1.getStatus()==1) {


                            StaticUtil.showToast(getActivity(), "Password update successfully");



                            final iOSDialog iOSDialog = new iOSDialog(getActivity());
                            iOSDialog.setTitle(getActivity().getString(R.string.app_name));
                            iOSDialog.setSubtitle("Password update successfully");
                            iOSDialog.setPositiveLabel("OK");
                            //iOSDialog.setNegativeLabel(getActivity().getString(R.string.Lbl_Cancel));
                            iOSDialog.setBoldPositiveLabel(true);

                            iOSDialog.setPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                                    session.logoutUser();
                                    iOSDialog.dismiss();
                                }
                            });
                            iOSDialog.show();


                        } else {

                            StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");

                        }

                    } else {


                        StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");


                    }

                } else {
                    StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");

                }


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");


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

    private boolean validated() {

        if (OldPasswordEditText.getText().toString().trim().equals("")) {
            OldPasswordEditText.setError("This field can not be blank.");
            OldPasswordEditText.requestFocus();
            return false;
        }

        if (NewEditText.getText().toString().trim().equals("")) {
            NewEditText.setError("This field can not be blank.");
            NewEditText.requestFocus();
            return false;
        }
        if (confirmEditText.getText().toString().trim().equals("")) {
            confirmEditText.setError("This field can not be blank.");
            confirmEditText.requestFocus();
            return false;
        }





        return true;

    }

}