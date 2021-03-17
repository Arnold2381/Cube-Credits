package com.example.cubestop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText username,password;
    private Button login;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Users");

    SharedPreferences name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        name = getSharedPreferences("DETAILS",MODE_PRIVATE);

        if(!name.getString("name","").equals("")){
            Toast.makeText(getApplicationContext(),"Welcome "+name.getString("name","")+"!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this,Maps.class);
            this.finish();
            startActivity(i);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = username.getText().toString();
                String pass = password.getText().toString();

                if(usr.equals("") || pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }
                else{
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(usr) && dataSnapshot.child(usr).child("Password").getValue().toString().equals(pass)){
                                Toast.makeText(getApplicationContext(),"Logged in successfully!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this,Maps.class);
                                i.putExtra("name",dataSnapshot.child(usr).child("Name").getValue().toString());
                                i.putExtra("user",usr);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                }
            }
        });
    }
}