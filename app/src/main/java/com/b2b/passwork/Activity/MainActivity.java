package com.b2b.passwork.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Adaptor.pageAdapter;
import com.b2b.passwork.Adaptor.workspaceDetailPageAdaptor;
import com.b2b.passwork.Fragment.BookMeeting;

import com.b2b.passwork.Fragment.Homefragment;
import com.b2b.passwork.Fragment.QRScanerFragment;
import com.b2b.passwork.Fragment.SettingFragment;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.workspace_interface;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity   {


    @BindView(R.id.bottomBar)
    SmoothBottomBar bottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        loadFragment(new Homefragment());
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                if(i==0){

                    loadFragment(new Homefragment());


                }else if(i==1){
                    loadFragment(new BookMeeting());


                }else if(i==2){
                    loadFragment(new QRScanerFragment());


                }else if(i==3){
                    loadFragment(new SettingFragment());


                }



                return false;
            }
        });






    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()

                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack("null")
                    .commit();

            return true;

        }
        return false;
    }





}