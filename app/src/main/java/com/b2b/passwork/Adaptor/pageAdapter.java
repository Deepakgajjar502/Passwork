package com.b2b.passwork.Adaptor;

import android.app.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.b2b.passwork.R;


public class pageAdapter extends PagerAdapter {

    private Activity activity;
    private int[] imagesArray;


    public pageAdapter(Activity activity, int[] imagesArray){

        this.activity = activity;
        this.imagesArray = imagesArray;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();

        View view = inflater.inflate(imagesArray[position], container, false);



        ((ViewPager)container).addView(view);

        return view;
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