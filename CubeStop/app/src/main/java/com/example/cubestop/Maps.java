package com.example.cubestop;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences name;
    SharedPreferences.Editor edit;
    private TextView title,addr,time,status,dis;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        String sp =  i.getStringExtra("name");
        String usr = i.getStringExtra("user");
        name = getSharedPreferences("DETAILS",MODE_PRIVATE);
        if(sp!=null && usr!=null) {
            edit = name.edit();
            edit.putString("user", usr);
            edit.putString("name", sp);
            edit.apply();
        }

        title = findViewById(R.id.name);
        addr = findViewById(R.id.addr);
        time = findViewById(R.id.timing);
        dis = findViewById(R.id.dist);
        status = findViewById(R.id.open);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child("Users").child(name.getString("user",""));
                DataSnapshot stops = dataSnapshot.child("Waystops");
                Waystop[] arr = new Waystop[Integer.parseInt(stops.getChildrenCount()+"")];
                int i=0;
                for(DataSnapshot ds: stops.getChildren()){
                    arr[i] = new Waystop(ds.child("Latitude").getValue(Double.class),ds.child("Longitude").getValue(Double.class),ds.getKey());
                    i++;
                }
                double d = dist(user.child("Latitude").getValue(Double.class),user.child("Longitude").getValue(Double.class),arr[0].getLatitude(),arr[0].getLongitude());
                int near = 0;
                for(int j=1;j<stops.getChildrenCount();j++) {
                    if (dist(user.child("Latitude").getValue(Double.class),user.child("Longitude").getValue(Double.class), arr[j].getLatitude(), arr[j].getLongitude()) < d) {
                        d = dist(user.child("Latitude").getValue(Double.class),user.child("Longitude").getValue(Double.class), arr[j].getLatitude(), arr[j].getLongitude());
                        near = j;
                    }
                }
                // Add a marker in nearest cubestop and move the camera
                LatLng[] cube = new LatLng[arr.length];
                for(int j=0;j<arr.length;j++){
                    cube[j] = new LatLng(arr[j].getLatitude(),arr[j].getLongitude());
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cube))
                            .position(cube[j]).title(arr[j].getName()));
                }
                LatLng self = new LatLng(user.child("Latitude").getValue(Double.class),user.child("Longitude").getValue(Double.class));
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car)).position(self).title("You are here"));
                CameraPosition marker = new CameraPosition.Builder().target(self).zoom(12).tilt(90).bearing(110).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(marker));
                title.setText(arr[near].getName());
                addr.setText(stops.child(arr[near].getName()).child("Address").getValue(String.class));
                dis.setText("Distance: "+d+" kms");
                time.setText(stops.child(arr[near].getName()).child("Timing").getValue(String.class));
                status.setText(stops.child(arr[near].getName()).child("Status").getValue(String.class));
                if(status.getText().equals("OPEN")){
                    status.setTextColor(Color.rgb(50,205,50));
                }
                else{
                    status.setTextColor(Color.RED);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
    public double dist(double lat1,double lng1, double lat2, double lng2){
        float[] results = new float[1];
        Location.distanceBetween(lat1, lng1, lat2, lng2, results);
        return (int)((results[0]/1000.0)*100)/100.0;
    }
}