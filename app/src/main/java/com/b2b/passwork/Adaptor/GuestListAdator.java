package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.Model.AddGuestModel;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GuestListAdator extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;

    private OnItemClickListener onItemClickListener;
    ArrayList<AddGuestModel> guestList;


    public GuestListAdator(ArrayList<AddGuestModel> guestList, WorkspaceDetail workspaceDetail) {
        this.context = workspaceDetail;
        this.guestList = guestList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_add_guest, viewGroup, false);
        return new GuestListAdator.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((GuestListAdator.viewHolder) holder).GuestName.setText(guestList.get(position).getGuestName());
            ((GuestListAdator.viewHolder) holder).GuestEmail.setText(guestList.get(position).getGuestEmailId());
            ((GuestListAdator.viewHolder) holder).GuestMobile.setText(guestList.get(position).getGuestMobile());


        } catch (Exception ex) {
            Log.d("Sri", "ex" + ex);
        }
    }


    @Override
    public int getItemCount() {

        return guestList.size();
    }


    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView GuestName;
        TextView GuestEmail;
        TextView GuestMobile;
        ImageView deleteRow;

        public viewHolder(View itemView) {
            super(itemView);
            GuestName = itemView.findViewById(R.id.edt_GuestName);
            GuestEmail = itemView.findViewById(R.id.edt_Email);
            GuestMobile = itemView.findViewById(R.id.edt_Mobile);
            deleteRow = itemView.findViewById(R.id.Delete);
            deleteRow.setOnClickListener(this);
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