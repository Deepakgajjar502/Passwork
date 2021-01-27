package com.b2b.passwork.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import static android.net.wifi.WifiConfiguration.Status.strings;

public class WorkSpaceListAdaptor extends RecyclerView.Adapter  {
    Context context;
    // List<CategoryInformation> arrayList;
    Integer[] OfficeImage;
    String[] OfficeName;
    String[] OfficeAddress;



    public WorkSpaceListAdaptor(FragmentActivity activity, String[] Name, Integer[] Image, String[] officeAddress) {
        this.context =  activity;
        this.OfficeName = Name;
        this.OfficeImage = Image;
        this.OfficeAddress = officeAddress;


    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_workspace_list, viewGroup, false);
        return new WorkSpaceListAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((WorkSpaceListAdaptor.viewHolder)holder).OfficeTitle.setText(OfficeName[position]);
            ((WorkSpaceListAdaptor.viewHolder)holder).OfficeAddress.setText(OfficeAddress[position]);
         ((WorkSpaceListAdaptor.viewHolder)holder).officeImage.setImageResource(OfficeImage[position]);
            ((WorkSpaceListAdaptor.viewHolder)holder).officeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(holder.itemView.getContext(), WorkspaceDetail.class);
                    holder.itemView.getContext().startActivity(intent);
                    ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
                }
            });

        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return OfficeName.length;
    }



    class viewHolder extends RecyclerView.ViewHolder  {
        ImageView officeImage;
        TextView OfficeTitle;
        TextView OfficeAddress;

        public viewHolder(View itemView) {
            super(itemView);
            officeImage = itemView.findViewById(R.id.office_image);
            OfficeTitle =  itemView.findViewById(R.id.title);
            OfficeAddress =  itemView.findViewById(R.id.address);
        }


    }

}
