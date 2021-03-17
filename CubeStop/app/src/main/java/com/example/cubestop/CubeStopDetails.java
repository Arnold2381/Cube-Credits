package com.example.cubestop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CubeStopDetails extends AppCompatActivity {

    private Button food,wash,shops,fuel;
    private TextView avail;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Waystops");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube_stop_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String cube = i.getStringExtra("cube");
        myRef = myRef.child(cube).child("Parking");

        food = findViewById(R.id.food_b);
        wash = findViewById(R.id.wash_b);
        shops = findViewById(R.id.shops_b);
        fuel = findViewById(R.id.fuel_b);
        avail = findViewById(R.id.avail);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ans = dataSnapshot.getValue(String.class);
                if(ans.equals("Not Available")){
                    avail.setTextColor(Color.RED);
                }
                else{
                    avail.setTextColor(Color.rgb(54,136,29));
                }
                avail.setText(ans);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CubeStopDetails.this,Description.class);
                in.putExtra("type","Food");
                in.putExtra("cube",cube);
                startActivity(in);
            }
        });

        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CubeStopDetails.this,Description.class);
                in.putExtra("type","Wash");
                in.putExtra("cube",cube);
                startActivity(in);
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CubeStopDetails.this,Description.class);
                in.putExtra("type","Shops");
                in.putExtra("cube",cube);
                startActivity(in);
            }
        });

        fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CubeStopDetails.this,Description.class);
                in.putExtra("type","Fuel");
                in.putExtra("cube",cube);
                startActivity(in);
            }
        });


    }
}