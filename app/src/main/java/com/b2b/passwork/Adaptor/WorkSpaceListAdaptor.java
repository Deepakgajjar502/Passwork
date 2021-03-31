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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.BookDesk;
import com.b2b.passwork.Activity.PollRequest;
import com.b2b.passwork.Activity.Service_Request;
import com.b2b.passwork.Fragment.BookMeeting;
import com.b2b.passwork.R;

public class WorkSpaceListAdaptor extends RecyclerView.Adapter  {
    Context context;
    // List<CategoryInformation> arrayList;
    Integer[] OfficeImage;
    String[] OfficeName;
    Integer[] BGColors;



    public WorkSpaceListAdaptor(FragmentActivity activity, String[] Name, Integer[] Image, Integer[] BGColor) {
        this.context =  activity;
        this.OfficeName = Name;
        this.OfficeImage = Image;
        this.BGColors = BGColor;



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_workspace_list, viewGroup, false);
        return new WorkSpaceListAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((WorkSpaceListAdaptor.viewHolder)holder).txtService.setText(OfficeName[position]);
            ((WorkSpaceListAdaptor.viewHolder)holder).cardView.setCardBackgroundColor(ContextCompat.getColor(context, BGColors[position]));
         ((WorkSpaceListAdaptor.viewHolder)holder).officeImage.setImageResource(OfficeImage[position]);
            ((WorkSpaceListAdaptor.viewHolder)holder).officeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 /*   if(position==0) {
                        Intent intent = new Intent(holder.itemView.getContext(), BookDesk.class);
                        holder.itemView.getContext().startActivity(intent);
                        ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    }else if(position==1){

                        loadFragment(new BookMeeting());
                    }else*/ if(position==0){

                        Intent intent = new Intent(holder.itemView.getContext(), PollRequest.class);
                        holder.itemView.getContext().startActivity(intent);
                        ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }else if(position==1){

                        Intent intent = new Intent(holder.itemView.getContext(), Service_Request.class);
                        holder.itemView.getContext().startActivity(intent);
                        ((Activity) view.getContext()).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }
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
        TextView txtService;
        CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);
            officeImage = itemView.findViewById(R.id.serviceicon);
            txtService =  itemView.findViewById(R.id.txtService);
            cardView =  itemView.findViewById(R.id.cardview);
        }


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            ((FragmentActivity)context).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("null")
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;

    }

}
