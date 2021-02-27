package com.b2b.passwork.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Activity.E_Pass;
import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

import retrofit2.Callback;

public class floor_adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<FloorsItem> floors;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    public floor_adaptor(Context bookDesk, List<FloorsItem> floors, String workspaceName, String workspaceId) {
        this.context =  bookDesk;
        this.floors = floors;
        this.workspaceName = workspaceName;
        this.workspaceId =workspaceId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_floor_detail, viewGroup, false);
        return new floor_adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
             int TotalVideo = floors.get(position).getAvailable()+floors.get(position).getBooked();
            ((floor_adaptor.viewHolder)holder).workSpaceTitle.setText(workspaceName);
            ((floor_adaptor.viewHolder)holder).FloorName.setText(floors.get(position).getFloorName());
            ((floor_adaptor.viewHolder)holder).avaibleSeats.setText(floors.get(position).getBooked()+"/"+TotalVideo);


        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return floors.size();
    }



    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView workSpaceTitle;
        TextView FloorName;
        TextView avaibleSeats;
        LinearLayout workspaceLayout;

        public viewHolder(View itemView) {
            super(itemView);
            workSpaceTitle =   itemView.findViewById(R.id.workspaceName);
            FloorName =   itemView.findViewById(R.id.FloorName);
            avaibleSeats =   itemView.findViewById(R.id.availableSeats);
            workspaceLayout =  itemView.findViewById(R.id.workspace);
            workspaceLayout.setOnClickListener(this);
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