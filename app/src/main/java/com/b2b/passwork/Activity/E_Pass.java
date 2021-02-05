package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class E_Pass extends AppCompatActivity {

    @BindView(R.id.officeImage)
    ImageView officeImage;
    @BindView(R.id.ImageBack)
    ImageView ImageBack;
    @BindView(R.id.back)
    FrameLayout back;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.txtEpass)
    TextView txtEpass;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__pass);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        Intent intent = new Intent(E_Pass.this, MainActivity.class);
        startActivity(intent);
    }
}