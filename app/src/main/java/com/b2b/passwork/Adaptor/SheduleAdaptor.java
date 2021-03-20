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

import com.b2b.passwork.Activity.E_Pass;
import com.b2b.passwork.Model.Upcoming.upcomingDataItem;
import com.b2b.passwork.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SheduleAdaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<upcomingDataItem> UpcomingScheduleList;
    public SheduleAdaptor(FragmentActivity activity, List<upcomingDataItem> UpcomingSchedule) {
        this.context =  activity;
        this.UpcomingScheduleList = UpcomingSchedule;

    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shedule_detail, viewGroup, false);
        return new SheduleAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            String BookType;

            String startDateTime =UpcomingScheduleList.get(position).getStartDatetime();
            SimpleDateFormat startinput = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
            SimpleDateFormat startoutput = new SimpleDateFormat("dd-MMM-yy");

            String endDateTime =UpcomingScheduleList.get(position).getEndDatetime();
            SimpleDateFormat endinput = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
            SimpleDateFormat endoutput = new SimpleDateFormat("dd-MMM-yy");
            String StartDate = "";
            String EndDate = "";
            try {
                Date   startDate = startinput.parse(startDateTime);
                Date   endtDate = endinput.parse(endDateTime);
                StartDate =endoutput.format(startDate);
                EndDate =endoutput.format(endtDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(UpcomingScheduleList.get(position).getType().equals("desk")){
                BookType = "Personal";
                ((SheduleAdaptor.viewHolder)holder).iconBook.setImageDrawable(context.getResources().getDrawable(R.drawable.desk_book_image));
                ((SheduleAdaptor.viewHolder)holder).BookingType.setText("Desk - "+UpcomingScheduleList.get(position).getSeats());
            }else {
                BookType = "Meeting";
                ((SheduleAdaptor.viewHolder)holder).iconBook.setImageDrawable(context.getResources().getDrawable(R.drawable.meeting_room_image));
                ((SheduleAdaptor.viewHolder)holder).BookingType.setText("Topic - "+UpcomingScheduleList.get(position).getMeetingTopic());
            }

            if(StartDate.equals(EndDate)){
                ((SheduleAdaptor.viewHolder)holder).OfficeDate.setText(StartDate);
            }else {
                ((SheduleAdaptor.viewHolder)holder).OfficeDate.setText(StartDate +" to "+EndDate);
            }


            ((SheduleAdaptor.viewHolder)holder).OfficeTitle.setText(BookType);
            ((SheduleAdaptor.viewHolder)holder).BookingNumber.setText("Booking No- "+ UpcomingScheduleList.get(position).getBookingNumber());

            String finalStartDate = StartDate;
            String finalEndDate = EndDate;
            ((SheduleAdaptor.viewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {

           @Override
                public void onClick(View v) {

               String SeatNumber = "";
               if(UpcomingScheduleList.get(position).getType().equals("desk")){
                   SeatNumber = "DESK-"+UpcomingScheduleList.get(position).getSeats();
               }else {
                   SeatNumber = "MEETING AGENDA-"+UpcomingScheduleList.get(position).getMeetingTopic();
               }


                    Intent intent = new Intent(holder.itemView.getContext(), E_Pass.class);
                    intent.putExtra("workspaceName", UpcomingScheduleList.get(position).getWorkspaceName());
                    intent.putExtra("SeatNumber", SeatNumber);
                    intent.putExtra("StartDate", finalStartDate);
                    intent.putExtra("EndDate", finalEndDate);
               intent.putExtra("workspaceId", UpcomingScheduleList.get(position).getWorkspaceId());
               intent.putExtra("Booking", UpcomingScheduleList.get(position).getBookingNumber());
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

        return UpcomingScheduleList.size();
    }



    class viewHolder extends RecyclerView.ViewHolder {
        TextView BookingNumber;
        TextView OfficeTitle;
        TextView OfficeDate;
        TextView BookingType;
        CardView cardView;
        ImageView iconBook;


        public viewHolder(View itemView) {
            super(itemView);
            BookingNumber = itemView.findViewById(R.id.BookingNumber);
            OfficeTitle =  itemView.findViewById(R.id.title);
            OfficeDate =  itemView.findViewById(R.id.dateShedule);
            BookingType =  itemView.findViewById(R.id.type);
            cardView =  itemView.findViewById(R.id.cardview);
            iconBook =  itemView.findViewById(R.id.icon_book);
        }
    }

}