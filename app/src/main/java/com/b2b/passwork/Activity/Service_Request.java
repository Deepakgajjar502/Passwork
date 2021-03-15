package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Fragment.ServiceCategory;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.fragment_position;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Service_Request extends AppCompatActivity implements fragment_position {

    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.pagination)
    TextView pagination;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__request);
        ButterKnife.bind(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Service_Request.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });

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

    @Override
    public void screenOpen(int screenPosition) {

        if (screenPosition == 1) {

            Title.setText("What can we assist you with ?");
            pagination.setText("1/4");
            view1.setBackgroundResource(R.drawable.bg_category);
            view2.setBackgroundResource(R.drawable.bg_book_btn);
            view3.setBackgroundResource(R.drawable.bg_book_btn);
            view4.setBackgroundResource(R.drawable.bg_book_btn);


        } else if (screenPosition == 2) {

            Title.setText("More Detail");
            pagination.setText("2/4");
            view1.setBackgroundResource(R.drawable.bg_book_btn);
            view2.setBackgroundResource(R.drawable.bg_category);
            view3.setBackgroundResource(R.drawable.bg_book_btn);
            view4.setBackgroundResource(R.drawable.bg_book_btn);
        } else if (screenPosition == 3) {

            Title.setText("When & Where");
            pagination.setText("3/4");
            view1.setBackgroundResource(R.drawable.bg_book_btn);
            view2.setBackgroundResource(R.drawable.bg_book_btn);
            view3.setBackgroundResource(R.drawable.bg_category);
            view4.setBackgroundResource(R.drawable.bg_book_btn);
        } else if (screenPosition == 4) {

            Title.setText("Additional detail (Optional)");
            pagination.setText("4/4");
            view1.setBackgroundResource(R.drawable.bg_book_btn);
            view2.setBackgroundResource(R.drawable.bg_book_btn);
            view3.setBackgroundResource(R.drawable.bg_book_btn);
            view4.setBackgroundResource(R.drawable.bg_category);
        }


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            Intent intent = new Intent(Service_Request.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }
}