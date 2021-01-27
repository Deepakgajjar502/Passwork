package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.R;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);







    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {

        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}