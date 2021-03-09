package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class SeatListAdaptor extends RecyclerView.Adapter {

    private OnItemClickListener onItemClickListener;
    private Context mContext;
    List<SeatsItem> seats;

    public SeatListAdaptor(WorkspaceLayout workspaceLayout, List<SeatsItem> seats) {
        this.mContext = workspaceLayout;
        this.seats = seats;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_grid_single, viewGroup, false);
        return new SeatListAdaptor.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


            ((SeatListAdaptor.viewHolder) holder).textView.setText(seats.get(position).getSeatId()+"");


            if(seats.get(position).isAvailable()){
                ((SeatListAdaptor.viewHolder) holder).imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_armchair));
            }else {
                ((SeatListAdaptor.viewHolder) holder).imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_armchair_selected));
                ((SeatListAdaptor.viewHolder) holder).imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.green));
            }

           /* ((SeatListAdaptor.viewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if(lastItemSelectedPos!= -1){

                        }

                    ((SeatListAdaptor.viewHolder) holder).imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_working_select));
                    lastItemSelectedPos = position;
                }
            });*/



        } catch (Exception ex) {
            Log.d("Sri", "ex" + ex);
        }
    }


    @Override
    public int getItemCount() {

        return seats.size();
    }


    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        RelativeLayout SeatLayout;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_image);
            textView = itemView.findViewById(R.id.grid_text);
            SeatLayout = itemView.findViewById(R.id.seatLayout);
           SeatLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}

