package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.SubCategory.SubCategoryModel;
import com.b2b.passwork.Model.TimeslotModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class AvaialbleTimeSlot_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<TimeslotModel> your_array_list;
    private static int lastCheckedPos = -1;
    private OnItemClickListener onItemClickListener;
    TimeSlot_Adaptor madapter;

    public AvaialbleTimeSlot_Adaptor(List<TimeslotModel> your_array_list, Context context, int lastCheckedPos) {
        this.context =  context;
        this.your_array_list = your_array_list;
        this.lastCheckedPos = lastCheckedPos;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_availalble_slot, viewGroup, false);
        return new AvaialbleTimeSlot_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((AvaialbleTimeSlot_Adaptor.viewHolder)holder).TimeSlot.setText(your_array_list.get(position).getTimeSlot() +" "+ your_array_list.get(position).getAM_PM()  );

            ((AvaialbleTimeSlot_Adaptor.viewHolder)holder).bind();

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

            TimeSlot.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void bind() {

            if(lastCheckedPos== -1){
                TimeSlot.setBackgroundResource(R.drawable.calender_select_bg);
                TimeSlot.setTextColor(Color.parseColor("#000000"));
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

    public TimeslotModel getSelect(){

        if(lastCheckedPos != -1){
            return your_array_list.get(lastCheckedPos);
        }
        return  null;
    }


}