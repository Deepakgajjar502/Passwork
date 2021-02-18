package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.workspaceDetailPageAdaptor;
import com.b2b.passwork.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class WorkspaceDetail extends AppCompatActivity implements View.OnClickListener {


    Integer[] SheduleOfficeImage = new Integer[]{R.drawable.office_e, R.drawable.office_f};

    workspaceDetailPageAdaptor adapter;
    @BindView(R.id.view_pager2)
    ViewPager viewPager2;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000; //
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.ImageBack)
    ImageView ImageBack;
    @BindView(R.id.back)
    FrameLayout back;
    @BindView(R.id.layout_Image)
    RelativeLayout layoutImage;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.parent)
    CoordinatorLayout parent;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.booking)
    Button booking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.workspace_detail_layout);
        ButterKnife.bind(this);


        adapter = new workspaceDetailPageAdaptor(WorkspaceDetail.this, SheduleOfficeImage);
        viewPager2.setAdapter(adapter);

        indicator.setViewPager(viewPager2);
        indicator.animatePageSelected(0);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3 - 1) {
                    currentPage = 0;
                }
                viewPager2.setCurrentItem(currentPage++, true);
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

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        Intent intent = new Intent(WorkspaceDetail.this, MainActivity.class);
        startActivity(intent);

    }
}
