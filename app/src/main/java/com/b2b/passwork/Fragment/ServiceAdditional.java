package com.b2b.passwork.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.b2b.passwork.Activity.E_Pass;
import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.interfaces.fragment_position;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceAdditional extends Fragment implements View.OnClickListener, fragment_position {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.addr_edittext)
    EditText addrEdittext;
    @BindView(R.id.btnNext)
    Button btnNext;
    Boolean btnEnable = true;


    fragment_position position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            position = (fragment_position) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_service_additional, container, false);
        ButterKnife.bind(this, view);
        screenOpen(4);
        btnNext.setOnClickListener(this);
        addrEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    btnEnable = false;
                    btnNext.setBackgroundColor(getResources().getColor(R.color.activated_color));
                } else {
                    btnEnable = true;
                    btnNext.setBackgroundColor(getResources().getColor(R.color.accent));
                }
                Log.e("afterTextChanged", editable.length() + "");
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:

                if (btnEnable) {

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);


                } else {


                }


                break;

        }
    }

    @Override
    public void screenOpen(int screenPossion) {
        position.screenOpen(screenPossion);
    }
}