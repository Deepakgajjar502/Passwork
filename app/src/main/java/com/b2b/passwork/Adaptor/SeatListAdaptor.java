package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.WorkspaceLayout;

import com.b2b.passwork.Model.SeatList.AvailablityItem;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatListAdaptor extends RecyclerView.Adapter {

    private OnItemClickListener onItemClickListener;
    private Context mContext;
    List<SeatsItem> seats;
    private static int lastCheckedPos = -1;
    List<AvailablityItem> avaiablity = new ArrayList<>();
    List<Integer> booked = new ArrayList<>();
    int TotalBooked = 0;
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

            ((SeatListAdaptor.viewHolder) holder).bind();
            avaiablity = seats.get(position).getAvailablity();


                boolean breakLoop = false;

                while (!breakLoop) {
                    for(int l=0; l<=avaiablity.size(); l++){

                        if(!avaiablity.get(l).isStatus()){
                            breakLoop = true;
                          //  booked.add(avaiablity.get(l).getSeatId());
                            ((SeatListAdaptor.viewHolder) holder).textView.setBackgroundResource(R.drawable.calender_booked_bg);
                            ((SeatListAdaptor.viewHolder) holder).textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                            booked.add(avaiablity.get(l).getSeatId());
                            Log.e("bookingId",avaiablity.get(l).getSeatId()+"");
                        }else {
                            ((SeatListAdaptor.viewHolder) holder).textView.setBackgroundResource(R.drawable.calender_select_bg);
                            ((SeatListAdaptor.viewHolder) holder).textView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                        }
                    }

                }







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
           // imageView = itemView.findViewById(R.id.grid_image);
            textView = itemView.findViewById(R.id.single_calendar_date);
          //  SeatLayout = itemView.findViewById(R.id.seatLayout);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void bind() {

            if(lastCheckedPos== -1){


               // textView.setBackgroundResource(R.drawable.bg_book_btn);
            }else {
                if(lastCheckedPos == getAdapterPosition()){
                    textView.setBackgroundResource(R.drawable.selected_calender_type);
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                }else {
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    textView.setBackgroundResource(R.drawable.calender_select_bg);
                }
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (contains(booked, seats.get(getAdapterPosition()).getSeatId())) {


                                Log.e("available", "yes");
                            } else {
                                Log.e("available", "No");
                                textView.setBackgroundResource(R.drawable.selected_calender_type);
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                                if (lastCheckedPos != getAdapterPosition()) {
                                    notifyItemChanged(lastCheckedPos);
                                    lastCheckedPos = getAdapterPosition();
                                    if (mContext instanceof WorkspaceLayout) {
                                        ((WorkspaceLayout) mContext).onClickonSeat(seats.get(lastCheckedPos).getSeatId());
                                    }

                                }
                            }

                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public boolean contains(final List<Integer> array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

}

