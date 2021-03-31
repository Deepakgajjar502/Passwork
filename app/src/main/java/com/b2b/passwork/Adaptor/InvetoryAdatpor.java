package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Fragment.InvontoryFramgment;
import com.b2b.passwork.Model.Room.GetBay.BaysItems;
import com.b2b.passwork.Model.Room.GetBay.InventoryItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewInventoryItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class InvetoryAdatpor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    ArrayList<NewInventoryItem> inventory;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    TimeSlot_Adaptor madapter;
    FragmentManager fragmentManager;
    InvontoryFramgment invontoryFramgment;
    String foorName, SelectDate, baySelecteDate, showDate;
    Boolean selectype;

    public InvetoryAdatpor(FragmentActivity activity, ArrayList<NewInventoryItem> rooms, String foorName, String selectedDate, Boolean selectype, String baySelectedDate, String showDate) {
        this.context =  activity;
        this.inventory = rooms;
        this.foorName = foorName;
        this.SelectDate = selectedDate;
        this.selectype = selectype;
        this.baySelecteDate = baySelectedDate;
        this.showDate = showDate;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_inventory, viewGroup, false);
        return new InvetoryAdatpor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

            ((InvetoryAdatpor.viewHolder)holder).BayName.setText(inventory.get(position).getBayName());
          ((InvetoryAdatpor.viewHolder)holder).FloorName.setText(inventory.get(position).getBayDescription());
        /*  if(selectype) {
              ((InvetoryAdatpor.viewHolder) holder).avaialbleSeats.setText("Available Seats : " + inventory.get(position).getAvailablity().get(0).getAvailable() + "/" + inventory.get(position).getAvailablity().get(0).getTotal());
          }*/
            ((InvetoryAdatpor.viewHolder)holder).LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, WorkspaceLayout.class);
                    intent.putExtra("floorName", foorName);
                    intent.putExtra("InvId", inventory.get(position).getBayId()+"");
                    intent.putExtra("InvName", inventory.get(position).getBayName()+"");
                    intent.putExtra("SelectDate", SelectDate);
                    intent.putExtra("baySelecteDate", baySelecteDate);
                    intent.putExtra("showDate", showDate);
                    context.startActivity(intent);


                }
            });





        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return inventory.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView FloorName;
        TextView BayName;
        TextView avaialbleSeats;
        ImageView imageView;

        LinearLayout LinearLayout;

        public viewHolder(View itemView) {
            super(itemView);

            BayName =   itemView.findViewById(R.id.tv_bay_name);
            FloorName =   itemView.findViewById(R.id.tv_floor_name);
            imageView =  itemView.findViewById(R.id.imageView);
            avaialbleSeats =  itemView.findViewById(R.id.tv_availalbe);
            LinearLayout = itemView.findViewById(R.id.lin_general_info);

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