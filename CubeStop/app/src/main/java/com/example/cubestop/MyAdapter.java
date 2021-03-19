package com.example.cubestop;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context c;
    int totalTabs;

    public MyAdapter(Context context,FragmentManager fm,int total_count) {
        super(fm);
        c=context;
        this.totalTabs=total_count;
    }



    @Override
    public Fragment getItem(int position) {
       switch (position)
       {
           case 0:
               History historyFragment=new History();
               return historyFragment;
           case 1:
               Redeem redeemFragment=new Redeem();
               return redeemFragment;
           default:return null;

       }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }


}
