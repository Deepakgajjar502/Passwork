package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.pageAdapter;
import com.b2b.passwork.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; //
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.login)
    Button login;
    private int[] layouts;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


        layouts = new int[]{
                R.layout.screen_a, R.layout.screen_b, R.layout.screen_c, R.layout.screen_d
        };

        PagerAdapter adapter = new pageAdapter(WelcomeActivity.this, layouts);
        viewPager.setAdapter(adapter);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        indicator.animatePageSelected(0);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 5 - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }


    @OnClick(R.id.login)
    public void onViewClicked() {

        Intent mainIntent = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(mainIntent);
        finish();



    }
}