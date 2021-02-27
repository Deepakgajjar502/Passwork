package com.b2b.passwork.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b2b.passwork.R;
import com.b2b.passwork.Utility.UserSessionManager;
import com.google.zxing.WriterException;

import java.util.HashMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
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
    UserSessionManager session;
    String CorporateId, Token, companyName, UserId, startDate, EndDate, Seats, workspaceName, bookingNo, workspaceId;

    @BindView(R.id.EmployeeName)
    TextView EmployeeName;
    @BindView(R.id.CompanyName)
    TextView CompanyName;
    @BindView(R.id.workSpaceName)
    TextView workSpaceName;
    @BindView(R.id.Epass_date)
    TextView EpassDate;
    @BindView(R.id.bookingNumber)
    TextView bookingNumber;
    @BindView(R.id.QRCode)
    ImageView QRCode;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    @BindView(R.id.QRCodeBtn)
    Button QRCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_e__pass);
        ButterKnife.bind(this);


        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Intent intent = getIntent();
        workspaceName = intent.getStringExtra("workspaceName");
        Seats = intent.getStringExtra("SeatNumber");
        startDate = intent.getStringExtra("StartDate");
        EndDate = intent.getStringExtra("EndDate");
        bookingNo = intent.getStringExtra("Booking");
        workspaceId = intent.getStringExtra("workspaceId");

        String firstName = user.get(UserSessionManager.KEY_FIRST_NAME);
        String LastName = user.get(UserSessionManager.KEY_LAST_NAME);
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);
        companyName = user.get(UserSessionManager.KEY_COMPANY_NAME);
        UserId = user.get(UserSessionManager.KEY_ID);

        bookingNumber.setText("Booking No.-" + bookingNo);
        EmployeeName.setText(firstName + " " + LastName);
        CompanyName.setText(companyName);
        txtEpass.setText("E-PASS / DESK-" + Seats);
        workSpaceName.setText(workspaceName);
        if (startDate.equals(EndDate)) {
            EpassDate.setText(startDate);
        } else {
            EpassDate.setText(startDate + " to " + EndDate);
        }

        CreateQRCode(bookingNo);

        QRCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(E_Pass.this, QRCodeScan.class);
                intent1.putExtra("booking",bookingNo );
                intent1.putExtra("workspaceId",workspaceId );
                startActivity(intent1);


            }
        });


    }

    private void CreateQRCode(String bookingNo) {

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(bookingNo, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            QRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }




    }

    @OnClick(R.id.back)
    public void onViewClicked() {

        Intent intent = new Intent(E_Pass.this, MainActivity.class);
        startActivity(intent);
    }


}