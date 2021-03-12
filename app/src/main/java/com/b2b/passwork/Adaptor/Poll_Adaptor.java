package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Fragment.OnGoingPollFragment;
import com.b2b.passwork.Model.PollList.OptionsItem;
import com.b2b.passwork.Model.PollList.QuestionsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Poll_Adaptor extends RecyclerView.Adapter implements OnItemClickListener {
    Context context;
    // List<CategoryInformation> arrayList;
    List<QuestionsItem> rollList;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    Poll_Answer_Adaptor madapter;
    List<OptionsItem> AnswerList;
    String fragment, pollId;
    List<String> selectedAns = new ArrayList<>();
    ProgressBar progressBar;
    OnGoingPollFragment activity;
    public Poll_Adaptor(Context context, List<QuestionsItem> questList, String fragment, String pollId, ProgressBar progressBar) {
        this.context =  context;
        this.rollList = questList;
        this.fragment = fragment;
        this.pollId = pollId;
        this.progressBar = progressBar;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_poll, viewGroup, false);
        return new Poll_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


            ((Poll_Adaptor.viewHolder)holder).txtQuest.setText(rollList.get(position).getQuestionText()+"");
            if(!rollList.get(position).isQuestionType()){
                if(fragment.equals("ongoing")){

                    ((Poll_Adaptor.viewHolder)holder).btnSubmit.setVisibility(View.VISIBLE);
                }else {
                    ((Poll_Adaptor.viewHolder)holder).btnSubmit.setVisibility(View.GONE);
                }
                ((Poll_Adaptor.viewHolder)holder).txtMultiselect.setVisibility(View.VISIBLE);

            }else {
                ((Poll_Adaptor.viewHolder)holder).txtMultiselect.setVisibility(View.GONE);
                ((Poll_Adaptor.viewHolder)holder).btnSubmit.setVisibility(View.GONE);
            }

            ((Poll_Adaptor.viewHolder)holder).txtvotes.setText(rollList.get(position).getTotalOfAnswers()+" votes");


            AnswerList = rollList.get(position).getOptions();


            madapter = new Poll_Answer_Adaptor( context, AnswerList, fragment, pollId, rollList.get(position).getQuestionId(), rollList.get(position).getTotalOfAnswers() ,  ((Poll_Adaptor.viewHolder)holder).btnSubmit, rollList.get(position).isQuestionType(), progressBar);
            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            ((Poll_Adaptor.viewHolder)holder).rec_Anw.setLayoutManager(horizontaLayoutManagaer);
            ((Poll_Adaptor.viewHolder)holder).rec_Anw.setAdapter(madapter);



        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return rollList.size();
    }

    @Override
    public void onItemClick(View v, int position) {


    }


    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView txtQuest;
        TextView txtMultiselect;
        TextView txtvotes;
        RecyclerView rec_Anw;
        Button btnSubmit;


        public viewHolder(View itemView) {
            super(itemView);
            txtQuest =   itemView.findViewById(R.id.txtQust);
            txtMultiselect =   itemView.findViewById(R.id.txtmultiSelect);
            txtvotes =   itemView.findViewById(R.id.votes);
            rec_Anw =   itemView.findViewById(R.id.rec_anw);
            btnSubmit =  itemView.findViewById(R.id.btnSubmit);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }



}
