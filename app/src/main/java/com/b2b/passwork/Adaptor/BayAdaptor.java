package com.b2b.passwork.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Fragment.InvontoryFramgment;
import com.b2b.passwork.Model.Room.BaysItem;
import com.b2b.passwork.Model.Room.GetBay.BaysItems;
import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewBaysItem;
import com.b2b.passwork.R;
import com.b2b.passwork.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class BayAdaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;
    List<NewBaysItem> rooms;
    String workspaceName, workspaceId;
    private OnItemClickListener onItemClickListener;
    TimeSlot_Adaptor madapter;
    FragmentManager fragmentManager;
    InvontoryFramgment invontoryFramgment;
    String SelectedDate, baySelectedDate, showDate;
    Boolean selectType;
    public BayAdaptor(FragmentActivity activity, List<NewBaysItem> rooms, String selectedDate, Boolean selectType, String baySelectedDate, String showSelectedDate) {
        this.context =  activity;
        this.rooms = rooms;
        this.SelectedDate = selectedDate;
        this.selectType = selectType;
        this.baySelectedDate = baySelectedDate;
        this.showDate = showSelectedDate;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bay_layout, viewGroup, false);
        return new BayAdaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {

            fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

           ((BayAdaptor.viewHolder)holder).BayName.setText(rooms.get(position).getBayParentName());
            ((BayAdaptor.viewHolder)holder).FloorName.setText(rooms.get(position).getFloorInfo().get(0).getFloorName());


          ((BayAdaptor.viewHolder)holder).LinearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  if( ((BayAdaptor.viewHolder)holder).frameLayout.getVisibility()==View.VISIBLE)
                  {
                      hideView(((BayAdaptor.viewHolder)holder).frameLayout, ((BayAdaptor.viewHolder)holder).imageView);


                  }
                  else
                  {
                      showView(((BayAdaptor.viewHolder)holder).frameLayout, ((BayAdaptor.viewHolder)holder).imageView);
                  }

              }
          });

            invontoryFramgment = InvontoryFramgment.newInstance(rooms.get(position).getInventory(), rooms.get(position).getFloorInfo().get(0).getFloorName() , SelectedDate, selectType, baySelectedDate, showDate);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_general_info, invontoryFramgment).commitAllowingStateLoss();

        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }


    @Override
    public int getItemCount() {

        return rooms.size();
    }




    class viewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener{

        TextView FloorName;
        TextView BayName;
        ImageView imageView;
        FrameLayout frameLayout;
        LinearLayout  LinearLayout;

        public viewHolder(View itemView) {
            super(itemView);

            BayName =   itemView.findViewById(R.id.tv_bay_name);
            FloorName =   itemView.findViewById(R.id.tv_floor_name);
            imageView =  itemView.findViewById(R.id.imageView);
            frameLayout = itemView.findViewById(R.id.container_general_info);
            LinearLayout = itemView.findViewById(R.id.lin_general_info);

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
    private void showView(View view, ImageView rubikLight)
    {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

        //toggling visibility
        view.setVisibility(View.VISIBLE);

        //adding sliding effect
        view.startAnimation(slideDown);
        rubikLight.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);

    }

    private void hideView(View view, ImageView rubikLight)
    {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        rubikLight.setImageResource(R.drawable.ic_arrow_drop_down_24);
        //toggling visibility
        view.setVisibility(View.GONE);

        //adding sliding effect
        view.startAnimation(slideUp);
    }




}