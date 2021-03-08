package com.b2b.passwork.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Adaptor.Poll_Adaptor;
import com.b2b.passwork.Adaptor.floor_adaptor;
import com.b2b.passwork.Model.AnswerModel;
import com.b2b.passwork.Model.PollModel;
import com.b2b.passwork.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnGoingPollFragment extends Fragment {


    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rec_poll)
    RecyclerView recPoll;
    ArrayList<PollModel> ListofPoll = new ArrayList<>();

    Poll_Adaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_on_going_poll, container, false);
        ButterKnife.bind(this, view);





        PollModel model = new PollModel("Who was the 11th PM of India?", true, false, "10", "15-03-2021");
        ListofPoll.add(model);
        model = new PollModel("Hitler party which came into power in 1993 is know as?", true, false, "15", "15-03-2021");
        ListofPoll.add(model);
        model = new PollModel("What are the best places to visit in Australia", true, true, "18", "15-03-2021");
        ListofPoll.add(model);

        adaptor = new Poll_Adaptor(getActivity(),ListofPoll);
        LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recPoll.setLayoutManager(horizontaLayoutManagaer);
        recPoll.setAdapter(adaptor);





        return view;
    }
}