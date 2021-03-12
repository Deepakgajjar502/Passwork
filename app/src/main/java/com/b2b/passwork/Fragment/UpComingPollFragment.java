package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Adaptor.Poll_listAdaptor;
import com.b2b.passwork.Model.PollList.PollListResponse;
import com.b2b.passwork.Model.PollList.pollDataItem;
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

public class UpComingPollFragment extends Fragment {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rec_poll_list)
    RecyclerView recPoll;
    ArrayList<pollDataItem> ListofPoll = new ArrayList<>();


    Poll_listAdaptor mAdaptor;

    UserSessionManager session;
    String Token, workspaceName, workspaceAddress;
    @BindView(R.id.noRecordFoundLayout)
    LinearLayout noRecordFoundLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_up_coming_poll, container, false);
        ButterKnife.bind(this, view);

        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);

        getPollListUpGoing();


        return view;
    }

    private void getPollListUpGoing() {


        progressBar.setVisibility(View.VISIBLE);
        Map<String, Integer> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("type", 2);
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
                            mAdaptor = new Poll_listAdaptor(getActivity(), ListofPoll, "upcoming", progressBar);
                            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recPoll.setLayoutManager(horizontaLayoutManagaer);
                            recPoll.setAdapter(mAdaptor);

                            if(ListofPoll.size()==0){
                                noRecordFoundLayout.setVisibility(View.VISIBLE);
                                recPoll.setVisibility(View.GONE);
                            }else {
                                noRecordFoundLayout.setVisibility(View.GONE);
                                recPoll.setVisibility(View.VISIBLE);
                            }


                        } else {

                            noRecordFoundLayout.setVisibility(View.VISIBLE);
                            recPoll.setVisibility(View.GONE);
                        }


                    } else {
                        noRecordFoundLayout.setVisibility(View.VISIBLE);
                        recPoll.setVisibility(View.GONE);
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