package com.b2b.passwork.Adaptor;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.Activity.WorkspaceDetail;
import com.b2b.passwork.R;

public class workspaceDetailPageAdaptor extends PagerAdapter {

    private Activity activity;
    private Integer[] imagesArray;

    public workspaceDetailPageAdaptor(WorkspaceDetail mainActivity, Integer[] sheduleOfficeImage) {
        this.activity = mainActivity;
        this.imagesArray = sheduleOfficeImage;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = activity.getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageScroll);
        imageView.setImageResource(imagesArray[position]);
        Log.e("imageSize",imagesArray.length+"");

        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}