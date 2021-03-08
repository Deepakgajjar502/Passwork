package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Model.CategoryModel;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class CateogryAdaptor extends RecyclerView.Adapter {
        Context context;
        // List<CategoryInformation> arrayList;
        ArrayList<CategoryModel> CategoryList;
        String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;



public CateogryAdaptor(FragmentActivity activity, ArrayList<CategoryModel> rooms) {
        this.context =  activity;
        this.CategoryList = rooms;
        }


@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category, viewGroup, false);
        return new CateogryAdaptor.viewHolder(view);
        }



@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {


        ((CateogryAdaptor.viewHolder)holder).txtCagetory.setText(CategoryList.get(position).getCategoryTitle());



        }catch (Exception ex){

        }
        }


@Override
public int getItemCount() {

        return CategoryList.size();

        }




class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

    TextView txtCagetory;



    public viewHolder(View itemView) {
        super(itemView);
        txtCagetory =   itemView.findViewById(R.id.txtCageogry);
        txtCagetory.setOnClickListener(this);

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