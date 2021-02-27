package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.R;
import com.b2b.passwork.Utility.UserSessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplaceScreen extends AppCompatActivity {


    @BindView(R.id.Logo)
    ImageView Logo;
    @BindView(R.id.appName)
    TextView appName;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splace_screen);
        ButterKnife.bind(this);
        session = new UserSessionManager(getApplicationContext());

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom);
        Logo.startAnimation(animation1);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        appName.startAnimation(animation);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(session.isUserLoggedIn()){

                    Intent mainIntent = new Intent(SplaceScreen.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                }else {

                Intent mainIntent = new Intent(SplaceScreen.this,WelcomeActivity.class);
                startActivity(mainIntent);
                finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}