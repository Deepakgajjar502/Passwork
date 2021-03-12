package com.b2b.passwork.Adaptor;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.b2b.passwork.Fragment.ChangePassword;
import com.b2b.passwork.Fragment.OnGoingPollFragment;
import com.b2b.passwork.Fragment.ResultPollFragment;
import com.b2b.passwork.Fragment.UpComingPollFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new OnGoingPollFragment();
        }
        else if (position == 1)
        {
            fragment = new UpComingPollFragment();
        }
        else if (position == 2)
        {
            fragment = new ResultPollFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "ONGOING";
        }
        else if (position == 1)
        {
            title = "UPCOMING";
        }
        else if (position == 2)
        {
            title = "RESULTS";
        }
        return title;
    }
}
