package com.example.cubestop;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences name;
    SharedPreferences.Editor edit;
    private TextView title,addr,time,status,dis;
    private Button details,test;
    private int near = 0;
    private Marker loc;
    private int i=0;
    private Waystop[] arr;
    private boolean flag = true;

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
        addr = findViewById(R.id.price);
        time = findViewById(R.id.timing);
        dis = findViewById(R.id.dist);
        status = findViewById(R.id.open);
        details = findViewById(R.id.details);
        test = findViewById(R.id.test);

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
                arr = new Waystop[Integer.parseInt(stops.getChildrenCount()+"")];
                int i=0;
                for(DataSnapshot ds: stops.getChildren()){
                    arr[i] = new Waystop(ds.child("Latitude").getValue(Double.class),ds.child("Longitude").getValue(Double.class),ds.getKey());
                    i++;
                }
                double d = dist(user.child("Latitude").getValue(Double.class),user.child("Longitude").getValue(Double.class),arr[0].getLatitude(),arr[0].getLongitude());
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
                loc = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car)).position(self).title("You are here"));
                CameraPosition marker = new CameraPosition.Builder().target(self).zoom(12).tilt(90).bearing(110).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(marker));
                title.setText(arr[near].getName());
                addr.setText(stops.child(arr[near].getName()).child("Address").getValue(String.class));
                dis.setText(d+" kms");
                time.setText(stops.child(arr[near].getName()).child("Timing").getValue(String.class));
                status.setText(stops.child(arr[near].getName()).child("Status").getValue(String.class));
                if(status.getText().equals("OPEN")){
                    status.setTextColor(Color.rgb(50,205,50));
                }
                else{
                    status.setTextColor(Color.RED);
                }
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Maps.this,CubeStopDetails.class);
                        i.putExtra("cube",arr[near].getName());
                        startActivity(i);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        LatLng[] latlng = {new LatLng(12.695224772579373, 77.91742195339859), new LatLng(12.687679683068348, 77.92678422760282),
        new LatLng(12.686885450104834, 77.9402170558089), new LatLng(12.67973724191854, 77.9532428286148),
        new LatLng(12.67378024848296, 77.97807320802605), new LatLng(12.670205985563031, 77.9898778146314)};
        CountDownTimer ct = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                loc.remove();
                loc = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car)).position(latlng[i]).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng[i++], 12));
                dis.setText(dist(arr[near].getLatitude(),arr[near].getLongitude(),latlng[i].latitude,latlng[i].longitude)+" kms");
            }
            @Override
            public void onFinish() {
            }
        };
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    ct.start();
                    flag = false;
                }
            }
        });
    }
    public double dist(double lat1,double lng1, double lat2, double lng2){
        float[] results = new float[1];
        Location.distanceBetween(lat1, lng1, lat2, lng2, results);
        double d =  (int)((results[0]/1000.0)*100)/100.0;
        if(d<10.0) notif();
        return d;
    }
    public void notif(){
        Intent intent = new Intent(this,CubeStopDetails.class);
        intent.putExtra("cube",arr[near].getName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationChannel channel = new NotificationChannel("0","Cube", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.cubestop);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel.getId())
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setLargeIcon(image)
            .setContentTitle("Cube Stop Nearby")
            .setContentText("You are approaching Chinnar Cube Stop. Wanna stop by and take a break !!!")
            .setStyle(new NotificationCompat.BigTextStyle()
            .bigText("You are approaching Chinnar Cube Stop. Wanna stop by and take a break !!!"))
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
}