package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class TimeSlot_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<String> your_array_list;

    private OnItemClickListener onItemClickListener;


    public TimeSlot_Adaptor(List<String> your_array_list, Context context) {
        this.context =  context;
        this.your_array_list = your_array_list;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_time_slot, viewGroup, false);
        return new TimeSlot_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((TimeSlot_Adaptor.viewHolder)holder).TimeSlot.setText(your_array_list.get(position));



        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return your_array_list.size();
    }



    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView TimeSlot;

        public viewHolder(View itemView) {
            super(itemView);
            TimeSlot =   itemView.findViewById(R.id.txttimeSlot);

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