package com.b2b.passwork.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeScan extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private CodeScanner mCodeScanner;
    String BookingNo, workspaceId;
    UserSessionManager session;
    String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_q_r_scaner);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        BookingNo = intent.getStringExtra("booking");
        workspaceId = intent.getStringExtra("workspaceId");

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);


        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                QRCodeScan.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getCheckIn(result.getText(), BookingNo);
                       // Toast.makeText(QRCodeScan.this, result.getText(), Toast.LENGTH_SHORT).show();
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


    }

    private void getCheckIn(String workspaceId, String bookingNo) {


        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("booking_number", bookingNo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<DefaultResponse> responseBody = RestManager.getInstance().getService()
                .getCheckIn(token, body);

        //"artist",
        responseBody.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        DefaultResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            OpenDialog(response1.getMessage());


                        } else {
                            OpenDialog(response1.getMessage());

                        }


                    } else {
                        OpenDialog("Booking not found");

                    }
                }else {
                    OpenDialog("Booking not found");
                    }


            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                OpenDialog("Someting went wrong");


            }
        });


    }

    private void OpenDialog(String Message) {

        final iOSDialog iOSDialog = new iOSDialog(this);
        iOSDialog.setTitle(this.getString(R.string.app_name));
        iOSDialog.setSubtitle(Message);
        iOSDialog.setPositiveLabel("Ok");
        iOSDialog.setBoldPositiveLabel(true);

        iOSDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(QRCodeScan.this, MainActivity.class);
                startActivity(intent);
                iOSDialog.dismiss();
            }
        });
        iOSDialog.show();

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