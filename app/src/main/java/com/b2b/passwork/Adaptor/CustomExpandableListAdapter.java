package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2b.passwork.Activity.SelectBookingMettingSpace;
import com.b2b.passwork.Activity.SelectBookingSpace;
import com.b2b.passwork.Model.Room.GetBay.InventoryItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewBaysItem;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewInventoryItem;
import com.b2b.passwork.R;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    List<NewBaysItem> rooms;
    private Context context;
    List<NewInventoryItem> listofInventory;
    NewInventoryItem inventoryItem;

    public CustomExpandableListAdapter(SelectBookingMettingSpace selectBookingMettingSpace, List<NewBaysItem> rooms) {
    this.context = selectBookingMettingSpace;
    this.rooms = rooms;

    }

    @Override
    public int getGroupCount() {
        return this.rooms.size();
    }

    @Override
    public int getChildrenCount(int i) {
        int size = 0;
        if(rooms.get(i).getInventory()!=null) {
            size = rooms.get(i).getInventory().size();
        }
        return size;
    }

    @Override
    public Object getGroup(int i) {
        return this.rooms.get(i);
    }

    @Override
    public Object getChild(int grpPosition, int childPosition) {
        return this.rooms.get(grpPosition).getInventory().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int grpPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int grpPosition, boolean isExpanded, View view, ViewGroup viewGroup) {



        if(view == null){

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_bay_layout, null);

        }
        TextView BayName = view.findViewById(R.id.tv_bay_name);
        TextView FloorName = view.findViewById(R.id.tv_floor_name);
        ImageView imageView = view.findViewById(R.id.imageView);

        if (isExpanded) {
            imageView.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
        } else {
            imageView.setImageResource(R.drawable.ic_arrow_drop_down_24);
        }


        BayName.setText(rooms.get(grpPosition).getBayParentName());
        if(rooms.get(grpPosition).getFloorInfo().get(0).getFloorName() !=null) {
            FloorName.setText(rooms.get(grpPosition).getFloorInfo().get(0).getFloorName());
        }

        return view;
    }

    @Override
    public View getChildView(int grpPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {



        if(view == null){

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_inventory, null);

        }
        inventoryItem = rooms.get(grpPosition).getInventory().get(childPosition);
        TextView BayName = view.findViewById(R.id.tv_bay_name);
        TextView Disp = view.findViewById(R.id.tv_floor_name);
        TextView avaible = view.findViewById(R.id.tv_availalbe);


        BayName.setText(inventoryItem.getBayName());
        Disp.setText(inventoryItem.getBayDescription());

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        view.startAnimation(animation);



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
