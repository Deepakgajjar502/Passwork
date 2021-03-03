package com.b2b.passwork.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.E_Pass;
import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.Model.Room.BaysItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Room_adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<BaysItem> rooms;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    TimeSlot_Adaptor madapter;


    public Room_adaptor(FragmentActivity activity, List<BaysItem> rooms) {
        this.context =  activity;
        this.rooms = rooms;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_room_list, viewGroup, false);
        return new Room_adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            List<String> your_array_list = new ArrayList<String>();
            your_array_list.add("10:00 AM");
            your_array_list.add("11:00 AM");
            your_array_list.add("12:00 AM");
            your_array_list.add("01:00 PM");
            your_array_list.add("02:00 PM");
            your_array_list.add("03:00 PM");
            your_array_list.add("04:00 PM");
            your_array_list.add("05:00 PM");
            your_array_list.add("06:00 PM");

            ((Room_adaptor.viewHolder)holder).TotalSeat.setText(rooms.get(position).getSeatsCount()+"");
            ((Room_adaptor.viewHolder)holder).RoomName.setText(rooms.get(position).getBayName());
            ((Room_adaptor.viewHolder)holder).RoomDisc.setText(rooms.get(position).getBayDescription());

            madapter = new TimeSlot_Adaptor(your_array_list, context);
            LinearLayoutManager horizontaLayoutManagaer = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            ((Room_adaptor.viewHolder)holder).timeSlot.setLayoutManager(horizontaLayoutManagaer);
            ((Room_adaptor.viewHolder)holder).timeSlot.setAdapter(madapter);

            /*((viewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    Intent intent = new Intent(holder.itemView.getContext(), WorkspaceDetail.class);
                    intent.putExtra("roomName",rooms.get(position).getBayName());
                    intent.putExtra("roomDis", rooms.get(position).getBayDescription());
                    intent.putExtra("capacity", rooms.get(position).getSeatsCount());
                    intent.putExtra("BayId", rooms.get(position).getBayId());
              *//*      intent.putExtra("workspaceId", UpcomingScheduleList.get(position).getWorkspaceId());
                    intent.putExtra("Booking", UpcomingScheduleList.get(position).getBookingNumber()); *//*
                    holder.itemView.getContext().startActivity(intent);
                    ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                }
            });*/


        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return rooms.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView TotalSeat;
        TextView RoomName;
        TextView RoomDisc;
        TextView txtAmenities;
        RecyclerView timeSlot;
        CardView cardview;

        public viewHolder(View itemView) {
            super(itemView);
            TotalSeat =   itemView.findViewById(R.id.TotalSeat);
            RoomName =   itemView.findViewById(R.id.RoomName);
            RoomDisc =   itemView.findViewById(R.id.Discription);
            txtAmenities = itemView.findViewById(R.id.Amemities);
            timeSlot =   itemView.findViewById(R.id.TimeSlot);
            cardview =   itemView.findViewById(R.id.cardview);
            cardview.setOnClickListener(this);
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