package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.pageAdapter;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.Serializable;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; //
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.skip)
    TextView Skiptxt;

    @BindView(R.id.txtnext)
    TextView txtNext;
    @BindView(R.id.btnLayout)
    RelativeLayout btnLayout;
    @BindView(R.id.getsStart)
    Button getsStart;

    private int[] layouts;
    //    CircleIndicator indicator;
    DotsIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


        Skiptxt.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        getsStart.setOnClickListener(this);

        layouts = new int[]{
                R.layout.screen_a, R.layout.screen_b, R.layout.screen_c
        };

        PagerAdapter adapter = new pageAdapter(WelcomeActivity.this, layouts);
        viewPager.setAdapter(adapter);
        indicator = (DotsIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);


       /* final Handler handler = new Handler();
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
        }, DELAY_MS, PERIOD_MS);*/





    }



    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.skip)
        {
            Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();

        } else if(view.getId()==R.id.txtnext)
        {
            if (currentPage == 2) {
                getsStart.setVisibility(View.VISIBLE);
                txtNext.setVisibility(View.GONE);
                Skiptxt.setVisibility(View.GONE);
            }
            viewPager.setCurrentItem(currentPage++, true);

        } else if(view.getId()==R.id.getsStart)
        {
            Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();

        }

    }
}