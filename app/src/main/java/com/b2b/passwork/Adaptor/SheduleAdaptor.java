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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.b2b.passwork.Activity.E_Pass;
import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.R;

public class SheduleAdaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    Integer[] OfficeImage;
    String[] OfficeName;
    String[] OfficeDate;

    String[] TypeofBook;
    public SheduleAdaptor(FragmentActivity activity, String[] Name, Integer[] Image, String[] officeDate, String[] typeofBook) {
        this.context =  activity;
        this.OfficeName = Name;
        this.OfficeImage = Image;
        this.OfficeDate = officeDate;
        this.TypeofBook = typeofBook;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shedule_detail, viewGroup, false);
        return new SheduleAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

           /* Picasso.get().load(OfficeImage[position])
                    .centerCrop()
                    .into(((WorkSpaceListAdaptor.viewHolder) holder).officeImage);*/
            ((SheduleAdaptor.viewHolder)holder).OfficeTitle.setText(OfficeName[position]);
            ((SheduleAdaptor.viewHolder)holder).OfficeDate.setText(OfficeDate[position]);
            ((SheduleAdaptor.viewHolder)holder).BookingType.setText(TypeofBook[position]);
            ((SheduleAdaptor.viewHolder)holder).officeImage.setImageResource(OfficeImage[position]);


            ((SheduleAdaptor.viewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {

           @Override
                public void onClick(View v) {

                    Intent intent = new Intent(holder.itemView.getContext(), E_Pass.class);
                    holder.itemView.getContext().startActivity(intent);
                    ((Activity) v.getContext()).overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
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



    class viewHolder extends RecyclerView.ViewHolder {
        ImageView officeImage;
        TextView OfficeTitle;
        TextView OfficeDate;
        TextView BookingType;
        CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);
           // officeImage = itemView.findViewById(R.id.imageOffice);
            OfficeTitle =  itemView.findViewById(R.id.title);
            OfficeDate =  itemView.findViewById(R.id.dateShedule);
            BookingType =  itemView.findViewById(R.id.type);
            cardView =  itemView.findViewById(R.id.cardview);
        }
    }

}