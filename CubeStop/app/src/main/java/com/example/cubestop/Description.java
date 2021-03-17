package com.example.cubestop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Description extends AppCompatActivity {

    private ImageView logo;
    private TextView title;
    private RecyclerView view;
    private NameAdapter nameAdapter;
    private ArrayList<CubeCard> card;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Waystops");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Intent i = getIntent();
        String cube = i.getStringExtra("cube");
        String type = i.getStringExtra("type");
        myRef = myRef.child(cube).child("Facilities").child(type);

        logo = findViewById(R.id.icon);
        title = findViewById(R.id.label);
        view = findViewById(R.id.recycler);
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(type.equals("Food")){
            logo.setImageResource(R.drawable.food_icon);
            title.setText("FOOD");
        }
        else{
            logo.setImageResource(R.drawable.shop_icon);
            logo.getLayoutParams().height=(int)(60 * (getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
            logo.getLayoutParams().width=(int)(60 * (getApplicationContext().getResources().getDisplayMetrics().density) + 0.5f);
            title.setText("SHOPS");
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                card = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    card.add(new CubeCard(ds.getKey(),ds.child("Description").getValue(String.class),ds.child("Url").getValue(String.class)));
                }
                nameAdapter = new NameAdapter(getApplicationContext(), card);
                view.setAdapter(nameAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameHolder> {

    private final Context context;

    private final List<CubeCard> list;

    public NameAdapter(Context context, List<CubeCard> list) {
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
        View view = inflater.inflate(R.layout.cube_card, parent, false);
        return new NameHolder(view);
    }

    @Override
    public void onBindViewHolder(NameHolder holder, int position) {
        final CubeCard nameDetails = list.get(position);
        holder.title.setText(nameDetails.getName());
        holder.desc.setText(nameDetails.getDesc());
        Glide.with(holder.img.getContext()).load(nameDetails.getImg_url()).into(holder.img);
        //btn to be added
    }

    static class NameHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView title, desc;
        private final Button btn;

        NameHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.logo);
            title = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            btn = itemView.findViewById(R.id.button);
        }
    }
}
