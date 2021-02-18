package com.b2b.passwork.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.SeatListAdaptor;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.SeatBookResponse;
import com.b2b.passwork.Model.SeatList.SeatListResponse;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.gdacciaro.iOSDialog.iOSDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkspaceLayout extends AppCompatActivity  implements View.OnClickListener,  OnItemClickListener {


    // int[] imageId = new int[]{R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working_select, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working, R.drawable.ic_working};
    //  String[] web = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.officeLayout)
    ImageView officeLayout;
    @BindView(R.id.grid)
    RecyclerView grid;

    String startDate, EndDate, workspaceId, floorId;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    UserSessionManager session;
    String Token, workspaceName, workspaceAddress;
    SeatListAdaptor adapter;
    List<SeatsItem> seats;
    int nPrevSelGridItem = -1;
    String selectedItem;
    @BindView(R.id.backButton)
    ImageView backButton;
    int maxSelectSeat = 1;
    String seatId = "";
    int selectedItemPos = -1;
    int lastItemSelectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace_layout);
        ButterKnife.bind(this);

        session = new UserSessionManager(this);
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        HashMap<String, String> workspace = session.getworkspaceList();
        workspaceName = workspace.get(UserSessionManager.IS_WORKSPACE_TILE);
        workspaceAddress = workspace.get(UserSessionManager.IS_WORKSPACE_ADDRESS);

        startDate = getIntent().getStringExtra("startDate");
        EndDate = getIntent().getStringExtra("endDate");
        workspaceId = getIntent().getStringExtra("workspace_id");
        floorId = getIntent().getStringExtra("floor_id");

        backButton.setOnClickListener(this);

        Title.setText(workspaceName);
        address.setText(workspaceAddress);
        grid.setLayoutManager(new GridLayoutManager(WorkspaceLayout.this, 6));


        getSeats(workspaceId, floorId, startDate, EndDate);






      //  bookseatAPI(seatId, workspaceId, startDate, EndDate);
    }


    private void bookseatAPI(String seatId, String workspaceId, String startDate, String endDate) {


        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("id", workspaceId);
        jsonParams.put("type", "desk");
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("facility_id", "1");
        jsonParams.put("guest_count", "0");
        jsonParams.put("meeting_topic", "asd");
        jsonParams.put("seats", seatId);
        jsonParams.put("guest_info", "");
        jsonParams.put("start_datetime", startDate + " 00:00");
        jsonParams.put("end_datetime", endDate + " 00:00");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SeatBookResponse> responseBody = RestManager.getInstance().getService()
                .getSAVEBOOK(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SeatBookResponse>() {
            @Override
            public void onResponse(Call<SeatBookResponse> call, Response<SeatBookResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        SeatBookResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            Intent intent = new Intent(WorkspaceLayout.this, SuccefullyDeskBook.class);
                            intent.putExtra("bookingNumber", response1.getBookingNumber());
                            intent.putExtra("startDate",startDate );
                            intent.putExtra("endDate",endDate );
                            intent.putExtra("workspaceName",workspaceName );
                            intent.putExtra("workspaceAddress",workspaceAddress );
                            intent.putExtra("seatId",seatId );
                            startActivity(intent);

                          //  successbook(response1.getBookingNumber());


                        } else {

                            StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SeatBookResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });









    }

    private void successbook(String bookingNumber) {

        final iOSDialog iOSDialog = new iOSDialog(this);
        iOSDialog.setTitle(this.getString(R.string.app_name));
        iOSDialog.setSubtitle("Your Desk Booked Successfully, Booking Number #"+bookingNumber);
        iOSDialog.setPositiveLabel("OK");

        iOSDialog.setBoldPositiveLabel(true);

        iOSDialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WorkspaceLayout.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


                iOSDialog.dismiss();
            }
        });
        iOSDialog.show();




    }

    private void getSeats(String workspaceId, String floorId, String startDate, String endDate) {

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("floor_id", floorId);
        jsonParams.put("start_datetime", startDate + " 00:00");
        jsonParams.put("end_datetime", endDate + " 00:00");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<SeatListResponse> responseBody = RestManager.getInstance().getService()
                .getSeatList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<SeatListResponse>() {
            @Override
            public void onResponse(Call<SeatListResponse> call, Response<SeatListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        SeatListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {
                            seats = response1.getSeats();

                            adapter = new SeatListAdaptor(WorkspaceLayout.this, seats);
                            adapter.setOnItemClickListener(WorkspaceLayout.this);
                            grid.setAdapter(adapter);


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<SeatListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(WorkspaceLayout.this, "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });


    }

    @Override
    public void onItemClick(View v, int position) {

        ImageView imageView = v.findViewById(R.id.grid_image);
        if(v.getId()==R.id.seatLayout)
        {


            for(int l=0; l<seats.size(); l++){


                if(seats.get(l).getSelected()){
                    seats.get(l).setSelected(false);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_working));
                    Log.e("seatId", seats.get(l).getSeatId()+"");
                    adapter.notifyItemChanged(l);
                }
            }


            seatId = seats.get(position).getSeatId()+"";
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_working_select));
            seats.get(position).setSelected(true);

            bookDailog();



        }
    }

    private void bookDailog() {

        Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.desk_book_dailog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        TextView btnBookNOw = (TextView) dialog.findViewById(R.id.Booking);
        TextView DeskTitle = (TextView) dialog.findViewById(R.id.deskTitle);
        TextView SelectedStartDate = (TextView) dialog.findViewById(R.id.BookingStartDate);
        TextView SelectedEndDate = (TextView) dialog.findViewById(R.id.BookingEndDate);
        TextView WorkspaceTitle = (TextView) dialog.findViewById(R.id.deskWorkspaceTitle);
        TextView WorkspaceAddress = (TextView) dialog.findViewById(R.id.deskWorkspaceAddress);

        WorkspaceTitle.setText(workspaceName);
        WorkspaceAddress.setText(workspaceAddress);
        DeskTitle.setText("Desk "+seatId);
        SelectedStartDate.setText("START DATE : "+ startDate);
        SelectedEndDate.setText("END DATE : "+ EndDate );

        btnBookNOw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bookseatAPI(seatId, workspaceId, startDate, EndDate);
            }
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backButton:
                Log.e("click", "working");

                Intent intent = new Intent(WorkspaceLayout.this, BookDesk.class);
                startActivity(intent);

                break;

        }
    }
}