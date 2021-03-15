package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.ViewPagerAdapter;
import com.b2b.passwork.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PollRequest extends AppCompatActivity implements View.OnClickListener {

    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_surveys_request);
        ButterKnife.bind(this);
        backButton.setOnClickListener(this);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.backButton:
                Log.e("click", "working");

                Intent intent = new Intent(PollRequest.this, MainActivity.class);
                startActivity(intent);

                break;

        }
    }
}