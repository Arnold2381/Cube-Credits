package com.example.cubestop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Profile extends AppCompatActivity {
    TabLayout menu;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        menu=findViewById(R.id.menu);
        viewPager=findViewById(R.id.viewPager);
        sharedPreferences=getSharedPreferences("DETAILS",MODE_PRIVATE);
        menu.addTab(menu.newTab().setText("HISTORY"));
        menu.addTab(menu.newTab().setText("REDEEM"));
        menu.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter myAdapter=new MyAdapter(this,getSupportFragmentManager(),menu.getTabCount());
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menu));
        menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}