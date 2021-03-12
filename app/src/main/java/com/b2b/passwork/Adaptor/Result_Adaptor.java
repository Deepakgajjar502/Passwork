package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.PollList.QuestionsItem;
import com.b2b.passwork.Model.PollList.pollDataItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class Result_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<pollDataItem> rollList;
    List<QuestionsItem> QuestList;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    Poll_Adaptor madapter;
    String fragment;
    ProgressBar progressBar;


    public Result_Adaptor(FragmentActivity activity, List<pollDataItem> rooms, String fragment) {
        this.context =  activity;
        this.rollList = rooms;
        this.fragment = fragment;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_poll_list, viewGroup, false);
        return new Result_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            Log.e("getPollDescription", rollList.get(position).getPollDescription());
            ((Poll_listAdaptor.viewHolder)holder).txtPollTItle.setText(rollList.get(position).getPollDescription()+"");
            ((Poll_listAdaptor.viewHolder)holder).txtDate.setText("End Date :"+rollList.get(position).getEndDate()+"");

            if(rollList.get(position).isPollType()){
                ((Poll_listAdaptor.viewHolder)holder).txtAnonymous.setVisibility(View.VISIBLE);
            }else {
                ((Poll_listAdaptor.viewHolder)holder).txtAnonymous.setVisibility(View.GONE);
            }



            QuestList = rollList.get(position).getQuestions();

          //  madapter = new Poll_Adaptor( context, QuestList, fragment, rollList.get(position).getPollId(), progressBar);
            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            ((Poll_listAdaptor.viewHolder)holder).QuesRecy.setLayoutManager(horizontaLayoutManagaer);
            ((Poll_listAdaptor.viewHolder)holder).QuesRecy.setAdapter(madapter);








        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return rollList.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView txtPollTItle;
        TextView txtDate;
        TextView txtAnonymous;
        RecyclerView QuesRecy;


        public viewHolder(View itemView) {
            super(itemView);
            txtPollTItle =   itemView.findViewById(R.id.PollTitle);
            txtDate =   itemView.findViewById(R.id.txtendDate);
            txtAnonymous =   itemView.findViewById(R.id.txtAnonymous);
            QuesRecy =  itemView.findViewById(R.id.rec_poll);

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

