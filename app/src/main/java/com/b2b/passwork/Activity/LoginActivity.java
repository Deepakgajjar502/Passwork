package com.b2b.passwork.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.Model.Login.LoginResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.imageView)
    GifImageView imageView;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.imgGmail)
    Button imgGmail;
    @BindView(R.id.Googlelogin_button)
    SignInButton GoogleloginButton;
    @BindView(R.id.FrameLayout2)
    FrameLayout FrameLayout2;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static final int RC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GoogleloginButton.setOnClickListener(this);
        imgGmail.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        session = new UserSessionManager(getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.Googlelogin_button:
                signIn();
                break;

            case R.id.imgGmail:

                GoogleloginButton.performClick();
                signIn();
                break;

            case R.id.btnLogin:


                if (validated()) {

                    doLoginApiCall();
                }


                break;



        }

    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }


    private void doLoginApiCall() {

        progressBar.setVisibility(View.VISIBLE);


        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("email", edtEmail.getText().toString());
        jsonParams.put("password", edtPassword.getText().toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<LoginResponse> responseBody = RestManager.getInstance().getService()
                .Login(body);
        //"artist",
        responseBody.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response ) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);

                    if(response.body() != null) {

                            LoginResponse response1 = response.body();

                        if (response.isSuccessful()) {


                            if(response1.getStatus()==1){


                                StaticUtil.showToast(LoginActivity.this, response1.getMessage());

                                Log.e("CorporateName", response1.getProfile().getCorporateName());

                                session.setUserLoginSession(response1.getProfile().getId(), response1.getProfile().getRole(),  response1.getProfile().getFirstName(), response1.getProfile().getLastName(),  response1.getProfile().getEmail(), response1.getToken(),response1.getProfile().getMobile() , response1.getProfile().getCorporateName(), response1.getProfile().getCorporateId()   );
                                session.setUserLogin(true);

                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }else {

                                StaticUtil.showIOSLikeDialog(LoginActivity.this, response1.getMessage());

                            }

                        } else {


                            StaticUtil.showIOSLikeDialog(LoginActivity.this, response1.getMessage());


                        }

                    }else {
                        StaticUtil.showIOSLikeDialog(LoginActivity.this, "Wrong username or password");
                    }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(LoginActivity.this, "Wrong username or password");
            }
        });


    }

    private boolean validated() {

        if (edtEmail.getText().toString().trim().equals("")) {
            edtEmail.setError("This field can not be blank.");
            edtEmail.requestFocus();
            return false;
        }

        if (edtPassword.getText().toString().trim().equals("")) {
            edtPassword.setError("This field can not be blank.");
            edtPassword.requestFocus();
            return false;
        }
        if (!StaticUtil.isValidEmail(edtEmail.getText().toString())) {
            edtEmail.setError((getResources().getString( R.string.enter_valid_email )));
            edtEmail.requestFocus();
            return false;
        }




        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // String idToken = account.getIdToken();
            String FirstName = account.getGivenName();
            String Email = account.getEmail();
            String googleId = account.getId();
            String FamilyName = account.getFamilyName();

            session.setUserLogin(true);

            Log.e("familyName", FamilyName);

            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();


       //     SocialLoginAPI(Email,FirstName, FamilyName, UserType , googleId, "google" );
            // TODO(developer): send ID Token to server and validate


        } catch (ApiException e) {
            Log.e("googleerror", "handleSignInResult:error", e);

        }
    }
}