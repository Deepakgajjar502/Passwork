package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
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
    Button btnNext;
    boolean btnDisable = false;

    private static int lastCheckedPos = -1;



    public CateogryAdaptor(FragmentActivity activity, ArrayList<CategoryModel> categoryList,  Button btnNext) {
        this.context =  activity;
        this.CategoryList = categoryList;
        this.btnNext = btnNext;

    }


    @Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category, viewGroup, false);
        return new CateogryAdaptor.viewHolder(view);
        }




@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ((CateogryAdaptor.viewHolder)holder).txtCagetory.setText(CategoryList.get(position).getCategoryTitle());

        ((CateogryAdaptor.viewHolder)holder).bind(CategoryList.get(position));

        }

@Override
public int getItemCount() {

        return CategoryList.size();

        }




class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

    TextView txtCagetory;
    RelativeLayout row_category;




    public viewHolder(View itemView) {
        super(itemView);
        txtCagetory =   itemView.findViewById(R.id.txtCageogry);
        row_category =  itemView.findViewById(R.id.row_Category);

        row_category.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onItemClickListener.onItemClick(view, getAdapterPosition());
    }

    public void bind(CategoryModel model) {

        if(lastCheckedPos== -1){
            txtCagetory.setBackgroundResource(R.drawable.bg_book_btn);
        }else {
            if(lastCheckedPos == getAdapterPosition()){
                txtCagetory.setBackgroundResource(R.drawable.bg_category);
            }else {
                txtCagetory.setBackgroundResource(R.drawable.bg_book_btn);
            }
        }
        row_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCagetory.setBackgroundResource(R.drawable.bg_category);
                if(lastCheckedPos != getAdapterPosition()){
                    notifyItemChanged(lastCheckedPos);
                    lastCheckedPos = getAdapterPosition();

                    btnNext.setBackgroundColor(context.getResources().getColor(R.color.accent)  );
                }


            }
        });

    }
}
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    public CategoryModel getSelect(){

    if(lastCheckedPos != -1){
        return CategoryList.get(lastCheckedPos);
    }
return  null;
    }

}