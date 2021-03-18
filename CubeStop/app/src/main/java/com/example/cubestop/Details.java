package com.example.cubestop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Details extends AppCompatActivity {

    private ImageView logo,bg,preview;
    private TextView title,name,desc;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Waystops");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String cube = i.getStringExtra("cube");
        String type = i.getStringExtra("type");
        myRef = myRef.child(cube).child("Facilities").child(type);
        title = findViewById(R.id.label);
        logo = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        bg = findViewById(R.id.bg);
        preview = findViewById(R.id.preview);

        if(type.equals("Wash")){
            title.setText("WASHROOMS");
            logo.setImageResource(R.drawable.wash_icon);
        }
        else{
            title.setTextColor(Color.WHITE);
            title.setText(type.toUpperCase());
            logo.setImageResource(R.drawable.fuel_icon);
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!type.equals("Wash")){
                    Glide.with(getBaseContext()).load(dataSnapshot.child("Bg").getValue(String.class)).into(bg);
                }
                else{
                    bg.setImageResource(R.drawable.wash_icon);
                }
                int x = (int)(408 * (getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
                int y = (int)(300 * (getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
                Glide.with(getBaseContext()).load(dataSnapshot.child("Preview").getValue(String.class)).override(x,y).into(preview);
                name.setText(dataSnapshot.child("Name").getValue(String.class).toUpperCase());
                desc.setText(dataSnapshot.child("Desc").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}