package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.Room_adaptor;
import com.b2b.passwork.Adaptor.TicketAdaptor;
import com.b2b.passwork.Model.Room.GetRoomResponse;
import com.b2b.passwork.Model.TicketDetail.TicketDetailDataItem;
import com.b2b.passwork.Model.TicketDetail.TicketDetailResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.retrofit.RestManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TicketDetailFragment extends Fragment {


    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.ticketDetail)
    RecyclerView ticketDetail;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    UserSessionManager session;
    String Token;
    ArrayList<TicketDetailDataItem> ticketList = new ArrayList<>();
    TicketAdaptor adaptor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ticket_detail, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();

        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);


        getMyTicket();


        return view;
    }

    private void getMyTicket() {

        progressBar.setVisibility(View.VISIBLE);


        String token = "Bearer " + Token;

        Call<TicketDetailResponse> responseBody = RestManager.getInstance().getService()
                .getTicket(token);

        //"artist",
        responseBody.enqueue(new Callback<TicketDetailResponse>() {
            @Override
            public void onResponse(Call<TicketDetailResponse> call, Response<TicketDetailResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        TicketDetailResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            ticketList = response1.getData();
                            Log.e("ticket", ticketList.get(0).getCategoryName());
                            adaptor = new TicketAdaptor(getActivity(),ticketList );
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            ticketDetail.setLayoutManager(horizontaLayoutManagaer);
                            ticketDetail.setAdapter(adaptor);


                        } else {


                        }


                    } else {
                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<TicketDetailResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());
            }
        });

    }
}