package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Model.Employee.EmployeesItem;
import com.b2b.passwork.Model.FloorList.FloorsItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.List;

public class EmployeeAdaptor  extends RecyclerView.Adapter {
    Context context;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    List<EmployeesItem> employees;



    public EmployeeAdaptor(WorkspaceLayout workspaceLayout, List<EmployeesItem> employees) {
        this.context = workspaceLayout;
        this.employees = employees;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_employee_select, viewGroup, false);
        return new EmployeeAdaptor.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            ((EmployeeAdaptor.viewHolder)holder).EmpolyeeName.setText(employees.get(position).getFirstName() + " " + employees.get(position).getLastName() );


        } catch (Exception ex) {
            Log.d("Sri", "ex" + ex);
        }
    }


    @Override
    public int getItemCount() {

        return employees.size();
    }


    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView EmpolyeeName;
        CheckBox cbSelect;
        RelativeLayout EmployeelistLayout;

        public viewHolder(View itemView) {
            super(itemView);
            cbSelect = itemView.findViewById(R.id.cbSelect);
            EmpolyeeName = itemView.findViewById(R.id.tvEmployee);
            EmployeelistLayout = itemView.findViewById(R.id.employeelistLayout);
            EmployeelistLayout.setOnClickListener(this);
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