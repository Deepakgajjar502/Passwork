package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2b.passwork.Activity.WorkspaceLayout;
import com.b2b.passwork.Model.SeatList.SeatsItem;
import com.b2b.passwork.R;

import java.util.List;

public class CustomGrid  extends BaseAdapter {
    private Context mContext;
    List<SeatsItem> seats;


    public CustomGrid(WorkspaceLayout workspaceLayout, List<SeatsItem> seats) {

        this.mContext = workspaceLayout;
        this.seats = seats;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return seats.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v;


        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_grid_single, null);
        }else{
            v = convertView;
        }

            TextView textView = (TextView) v.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)v.findViewById(R.id.grid_image);
            textView.setText(seats.get(position).getSeatId()+"");
            if(seats.get(position).isAvailable()){
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_working));
        }else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_working_select));
            }

        return v;
    }
}