package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.AnswerModel;
import com.b2b.passwork.Model.PollModel;
import com.b2b.passwork.Model.Room.BaysItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Poll_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<PollModel> rollList;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    TimeSlot_Adaptor madapter;
    List<AnswerModel> AnswerList;

    public Poll_Adaptor(FragmentActivity activity, List<PollModel> rooms) {
        this.context =  activity;
        this.rollList = rooms;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_poll, viewGroup, false);
        return new Poll_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


            ((Poll_Adaptor.viewHolder)holder).txtQuest.setText(rollList.get(position).getQues()+"");
            if(rollList.get(position).getMultiSelect()){
                ((Poll_Adaptor.viewHolder)holder).txtMultiselect.setVisibility(View.VISIBLE);
                ((Poll_Adaptor.viewHolder)holder).btnSubmit.setVisibility(View.VISIBLE);
            }else {
                ((Poll_Adaptor.viewHolder)holder).txtMultiselect.setVisibility(View.GONE);
                ((Poll_Adaptor.viewHolder)holder).btnSubmit.setVisibility(View.GONE);
            }
            if(rollList.get(position).getAnonymous()){
                ((Poll_Adaptor.viewHolder)holder).txtAmony.setVisibility(View.VISIBLE);
            }else {
                ((Poll_Adaptor.viewHolder)holder).txtAmony.setVisibility(View.GONE);
            }
            ((Poll_Adaptor.viewHolder)holder).txtvotes.setText(rollList.get(position).getTotalParticipaent()+" votes");
            ((Poll_Adaptor.viewHolder)holder).txtDate.setText("End Date : "+rollList.get(position).getEndDate()+"");


      /*      madapter = new TimeSlot_Adaptor(your_array_list, context);
            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            ((Room_adaptor.viewHolder)holder).timeSlot.setLayoutManager(horizontaLayoutManagaer);
            ((Room_adaptor.viewHolder)holder).timeSlot.setAdapter(madapter);*/




        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return rollList.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView txtQuest;
        TextView txtMultiselect;
        TextView txtAmony;
        TextView txtvotes;
        TextView txtDate;
        Button btnSubmit;


        public viewHolder(View itemView) {
            super(itemView);
            txtQuest =   itemView.findViewById(R.id.txtQust);
            txtMultiselect =   itemView.findViewById(R.id.txtmultiSelect);
            txtAmony =   itemView.findViewById(R.id.txtAnonymous);
            txtvotes =   itemView.findViewById(R.id.votes);
            txtDate =   itemView.findViewById(R.id.txtendDate);
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
