package com.example.cubestop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TabLayout menu;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    private TextView nam,phone,email,cust,credits;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    private ImageButton logout,maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        menu=findViewById(R.id.menu);
        viewPager=findViewById(R.id.viewPager);
        sharedPreferences=getSharedPreferences("DETAILS",MODE_PRIVATE);
        Intent i = getIntent();
        String sp =  i.getStringExtra("name");
        String usr = i.getStringExtra("user");
        if(sp!=null && usr!=null) {
            edit = sharedPreferences.edit();
            edit.putString("user", usr);
            edit.putString("name", sp);
            edit.apply();
        }

        nam = findViewById(R.id.textView);
        phone = findViewById(R.id.textView2);
        email = findViewById(R.id.textView3);
        cust = findViewById(R.id.textView5);
        credits = findViewById(R.id.textView6);
        logout = findViewById(R.id.logout);
        maps = findViewById(R.id.back);

        maps.setOnClickListener(view -> {
            Intent j = new Intent(Profile.this,Maps.class);
            startActivity(j);
        });

        logout.setOnClickListener(view -> {
            edit = sharedPreferences.edit();
            edit.clear();
            edit.apply();
            Intent k = new Intent(Profile.this,MainActivity.class);
            startActivity(k);
            finish();
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = sharedPreferences.getString("user","");
                nam.setText(dataSnapshot.child(user).child("Name").getValue(String.class));
                phone.setText(dataSnapshot.child(user).child("Phone").getValue(String.class));
                email.setText(dataSnapshot.child(user).child("Email").getValue(String.class));
                cust.setText(user);
                credits.setText(dataSnapshot.child(user).child("Credits").getValue(Double.class)+"");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

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