package com.b2b.passwork.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.b2b.passwork.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;




public class QRScanerFragment extends Fragment    {

    private CodeScanner mCodeScanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_q_r_scaner, container, false);


                final Activity activity = getActivity();
                CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
                mCodeScanner = new CodeScanner(activity, scannerView);
                mCodeScanner.setDecodeCallback(new DecodeCallback() {
                    @Override
                    public void onDecoded(@NonNull final Result result) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                scannerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCodeScanner.startPreview();
                    }
                });

                //Do something after 100ms




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}