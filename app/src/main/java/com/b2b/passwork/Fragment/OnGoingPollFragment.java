package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Adaptor.Poll_Adaptor;
import com.b2b.passwork.Adaptor.Poll_listAdaptor;
import com.b2b.passwork.Adaptor.SheduleAdaptor;
import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Model.AnswerModel;
import com.b2b.passwork.Model.PollList.PollListResponse;
import com.b2b.passwork.Model.PollList.pollDataItem;
import com.b2b.passwork.Model.PollModel;
import com.b2b.passwork.Model.Upcoming.UpComingResponse;
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

public class OnGoingPollFragment extends Fragment {


    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rec_poll_list)
    RecyclerView recPoll;
    ArrayList<pollDataItem> ListofPoll = new ArrayList<>();


    Poll_listAdaptor mAdaptor;

    UserSessionManager session;
    String Token, workspaceName, workspaceAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_on_going_poll, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);




       /* PollModel model = new PollModel("Who was the 11th PM of India?", true, false, "10", "15-03-2021");
        ListofPoll.add(model);
        model = new PollModel("Hitler party which came into power in 1993 is know as?", true, false, "15", "15-03-2021");
        ListofPoll.add(model);
        model = new PollModel("What are the best places to visit in Australia", true, true, "18", "15-03-2021");
        ListofPoll.add(model);*/

       /* adaptor = new Poll_Adaptor(getActivity(),ListofPoll);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recPoll.setLayoutManager(horizontaLayoutManagaer);
        recPoll.setAdapter(adaptor);*/

        getPollListOnGoing();




        return view;
    }

    private void getPollListOnGoing() {


        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("type", "1");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        String token = "Bearer " + Token;

        Call<PollListResponse> responseBody = RestManager.getInstance().getService()
                .GetPollList(token, body);

        //"artist",
        responseBody.enqueue(new Callback<PollListResponse>() {
            @Override
            public void onResponse(Call<PollListResponse> call, Response<PollListResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        PollListResponse response1 = response.body();

                        if (response1.getStatus() == 1) {

                            ListofPoll = response1.getData();
                            mAdaptor = new Poll_listAdaptor(getActivity(),ListofPoll);
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recPoll.setLayoutManager(horizontaLayoutManagaer);
                            recPoll.setAdapter(mAdaptor);


                        } else {


                        }


                    } else {

                        StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                    }
                }

            }

            @Override
            public void onFailure(Call<PollListResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);
                StaticUtil.showIOSLikeDialog(getActivity(), "Someting went wrong");
                Log.e("error", t.getMessage().toString());

            }
        });


    }
}