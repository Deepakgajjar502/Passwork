package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.TimeDurationModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class SelectedEmployeeAdapter extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<EmployeesItem> selectedEmplyee;

    private OnItemClickListener onItemClickListener;


    public SelectedEmployeeAdapter(List<EmployeesItem> your_array_list, Context context) {
        this.context =  context;
        this.selectedEmplyee = your_array_list;


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_select_employee, viewGroup, false);
        return new SelectedEmployeeAdapter.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((SelectedEmployeeAdapter.viewHolder)holder).TimeSlot.setText(selectedEmplyee.get(position).getFirstName()+ " "+ selectedEmplyee.get(position).getLastName()  );



        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return selectedEmplyee.size();
    }



    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView TimeSlot;
        ImageView imageViewDelete;

        public viewHolder(View itemView) {
            super(itemView);
            TimeSlot =   itemView.findViewById(R.id.txtEmplyeeName);
            imageViewDelete = itemView.findViewById(R.id.Deleteimage);
            imageViewDelete.setOnClickListener(this);
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