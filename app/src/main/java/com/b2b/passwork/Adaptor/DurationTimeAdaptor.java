package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.TimeDurationModel;
import com.b2b.passwork.Model.TimeslotModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class DurationTimeAdaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<TimeDurationModel> your_array_list;

    private OnItemClickListener onItemClickListener;
    private static int lastCheckedPos = -1;

    public DurationTimeAdaptor(List<TimeDurationModel> your_array_list, Context context, int lastCheckedPos) {
        this.context =  context;
        this.your_array_list = your_array_list;
        this.lastCheckedPos = lastCheckedPos;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_time_duration, viewGroup, false);
        return new DurationTimeAdaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((DurationTimeAdaptor.viewHolder)holder).TimeSlot.setText(your_array_list.get(position).getTimeDuration() + " "+ your_array_list.get(position).getType() );

            ((DurationTimeAdaptor.viewHolder)holder).bind();

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
            TimeSlot =   itemView.findViewById(R.id.txttimeduration);
            TimeSlot.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void bind() {

            if(lastCheckedPos== -1){
                TimeSlot.setBackgroundResource(R.drawable.calender_select_bg);
            }else {
                if(lastCheckedPos == getAdapterPosition()){
                    TimeSlot.setBackgroundResource(R.drawable.selected_calender_type);
                    TimeSlot.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    TimeSlot.setBackgroundResource(R.drawable.calender_select_bg);
                    TimeSlot.setTextColor(Color.parseColor("#000000"));
                }
            }
            TimeSlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimeSlot.setBackgroundResource(R.drawable.selected_calender_type);
                    TimeSlot.setTextColor(Color.parseColor("#ffffff"));
                    if(lastCheckedPos != getAdapterPosition()){
                        notifyItemChanged(lastCheckedPos);
                        lastCheckedPos = getAdapterPosition();


                    }


                }
            });


        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public TimeDurationModel getSelect(){

        if(lastCheckedPos != -1){
            return your_array_list.get(lastCheckedPos);
        }
        return  null;
    }


}