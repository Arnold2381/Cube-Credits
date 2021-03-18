package com.example.cubestop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private RecyclerView view;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuCard> card;
    private TextView label;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Waystops");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        label = findViewById(R.id.label);
        view = findViewById(R.id.recycler);
        view.setHasFixedSize(true);
        view.getRecycledViewPool().setMaxRecycledViews(0,0);
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent i = getIntent();
        String cube = i.getStringExtra("cube");
        String type = i.getStringExtra("type");
        String name = i.getStringExtra("name");
        label.setText(name.toUpperCase());
        myRef = myRef.child(cube).child("Facilities").child(type).child(name).child("Menu");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                card = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String c = ds.getKey();
                    String t = ds.child("Time").getValue(String.class);
                    card.add(new MenuCard("","",c.toUpperCase(),t,true,false));
                    for(DataSnapshot ds1: ds.child("Items").getChildren()){
                        card.add(new MenuCard(ds1.getKey(),ds1.child("Price").getValue(String.class),c,t,false,ds1.child("Veg").getValue(Boolean.class)));
                    }
                }
                card.add(new MenuCard("","","","",true,false));
                menuAdapter = new MenuAdapter(getApplicationContext(), card);
                view.setAdapter(menuAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.NameHolder> {

    private final Context context;

    private final List<MenuCard> list;

    public MenuAdapter(Context context, List<MenuCard> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    @NonNull
    public NameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_card, parent, false);
        return new NameHolder(view);
    }

    @Override
    public void onBindViewHolder(NameHolder holder, int position) {
        final MenuCard nameDetails = list.get(position);
        if(nameDetails.getFrst()) {
            holder.title.setText(nameDetails.getCat());
            holder.price.setText(nameDetails.getTime());
            holder.btn.setText("");
            holder.btn.setBackgroundColor(Color.TRANSPARENT);
            holder.img.getLayoutParams().width = 0;
            holder.img.getLayoutParams().height = 0;
            ConstraintLayout.LayoutParams l = (ConstraintLayout.LayoutParams) holder.img.getLayoutParams();
            l.setMargins((int)(20 * (context.getResources().getDisplayMetrics().density) + 0.5f),(int)(5 * (context.getResources().getDisplayMetrics().density) + 0.5),0,0);
            holder.cv.setCardBackgroundColor(Color.TRANSPARENT);
            holder.cv.setRadius(0);
            holder.cv.setStrokeColor(Color.TRANSPARENT);
            holder.cv.setStrokeWidth(0);
            holder.layout.setMaxHeight((int)(60 * (context.getResources().getDisplayMetrics().density) + 0.5f));
        }
        else{
            holder.title.setText(nameDetails.getName());
            holder.price.setText(nameDetails.getPrice());
            if(nameDetails.getVeg()){
                holder.img.setImageResource(R.drawable.veg);
            }
            else{
                holder.img.setImageResource(R.drawable.nonveg);
            }
        }
    }

    static class NameHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView title, price;
        private final Button btn;
        private final MaterialCardView cv;
        private ConstraintLayout layout;

        NameHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.cons);
            cv = itemView.findViewById(R.id.card);
            img = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            btn = itemView.findViewById(R.id.button);
        }
    }
}