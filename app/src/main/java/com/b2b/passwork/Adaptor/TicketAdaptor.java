package com.b2b.passwork.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Activity.PollRequest;
import com.b2b.passwork.Activity.Service_Request;
import com.b2b.passwork.Fragment.BookMeeting;
import com.b2b.passwork.Model.TicketDetail.TicketDetailDataItem;
import com.b2b.passwork.R;

import java.util.ArrayList;

public class TicketAdaptor extends RecyclerView.Adapter {
    Context context;
    ArrayList<TicketDetailDataItem> ticketList;


    public TicketAdaptor(FragmentActivity activity, ArrayList<TicketDetailDataItem> ticketList) {
        this.context = activity;
        this.ticketList = ticketList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ticket, viewGroup, false);
        return new TicketAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            Log.e("ticketNo.", ticketList.get(position).getId()+"");
            ((TicketAdaptor.viewHolder) holder).TicketNo.setText("Ticket No. :"+ticketList.get(position).getId()+"");
            ((TicketAdaptor.viewHolder) holder).TicketStauts.setText("Ticket Status :" +ticketList.get(position).getStatus()+"");
            ((TicketAdaptor.viewHolder) holder).TicketCatogory.setText("Cateogry :"+ticketList.get(position).getCategoryName()+"("+ticketList.get(position).getSubCategoryName()+")");
            ((TicketAdaptor.viewHolder) holder).TicketDis.setText("Detail : "+ ticketList.get(position).getDescription()+"");

        } catch (Exception ex) {
            Log.d("Sri", "ex" + ex);
        }
    }


    @Override
    public int getItemCount() {

        return ticketList.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        ImageView officeImage;
        TextView TicketNo;
        TextView TicketStauts;
        TextView TicketCatogory;
        TextView TicketDis;

        public viewHolder(View itemView) {
            super(itemView);
            TicketNo = itemView.findViewById(R.id.edt_TicketNo);
            TicketStauts = itemView.findViewById(R.id.edt_Status);
            TicketCatogory = itemView.findViewById(R.id.edt_Category);
            TicketDis = itemView.findViewById(R.id.edt_Dis);
        }


    }



}