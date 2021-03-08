package com.b2b.passwork.Activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Fragment.Homefragment;
import com.b2b.passwork.Fragment.ServiceCategory;
import com.b2b.passwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Service_Request extends AppCompatActivity {

    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__request);
        ButterKnife.bind(this);


        loadFragment(new ServiceCategory());


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