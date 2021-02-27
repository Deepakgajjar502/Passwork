package com.b2b.passwork.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Fragment.BookMeeting;

import com.b2b.passwork.Fragment.Homefragment;
import com.b2b.passwork.Fragment.SettingFragment;
import com.b2b.passwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity   {


    @BindView(R.id.bottomBar)
    SmoothBottomBar bottomBar;

    boolean doubleBackToExitPressedOnce = false;

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


    @Override
    public void onBackPressed() {
          if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finishAffinity();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;


                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            }


            // or just go back to main activity


        }




}