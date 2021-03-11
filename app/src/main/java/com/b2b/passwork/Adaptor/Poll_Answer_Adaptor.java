package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.AnswerModel;
import com.b2b.passwork.Model.PollList.OptionsItem;
import com.b2b.passwork.Model.PollList.QuestionsItem;
import com.b2b.passwork.Model.PollList.pollDataItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class Poll_Answer_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;

    private OnItemClickListener onItemClickListener;
    List<OptionsItem> AnswerList;

    public Poll_Answer_Adaptor(Context activity, List<OptionsItem> rooms) {
        this.context =  activity;
        this.AnswerList = rooms;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_anwser, viewGroup, false);
        return new Poll_Answer_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


            ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_option.setText(AnswerList.get(position).getAnswerText()+"");
          //  ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_percent.setText("End Date :"+rollList.get(position).getEndDate()+"");






        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return AnswerList.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{


        TextView txtTv_percent;
        TextView txtTv_option;
        SeekBar seekBar;



        public viewHolder(View itemView) {
            super(itemView);
            seekBar =   itemView.findViewById(R.id.seek_bar);
            txtTv_option =   itemView.findViewById(R.id.tv_option);
            txtTv_percent =   itemView.findViewById(R.id.tv_percent);

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
