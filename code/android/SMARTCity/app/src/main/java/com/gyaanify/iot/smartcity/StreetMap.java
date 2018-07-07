package com.gyaanify.iot.smartcity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class StreetMap extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private FirebaseAuth mAuth;
    TextView name;
    String email;
    private  GoogleMap mMap;
    private Integer value,valuei;
    private int child_load = 1;
    private String ipgat = "192.168.225.";
    int[] ip;
    double []lat;
    double [] longi;
    Button button1;
    private static final String TAG = MapsActivityRaw.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_map);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            email = mAuth.getCurrentUser().getEmail();
        }
        else
            email = "Offline Access";



        name = (TextView) findViewById(R.id.login_name);
        name.setText("You are logged in as Admin via "+email);

        ip = new int[100];
        lat = new double[100];
        longi = new double[100];

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("lamp_no");

        myRef.addValueEventListener(new ValueEventListener() {

            int i;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(Integer.class);
                //Log.d(TAG, "Value is: " + value);
                //Toast.makeText(StreetMap.this,value.toString(),Toast.LENGTH_LONG).show();

                for(i=1;i<=value;i++){


                    FirebaseDatabase database_child = FirebaseDatabase.getInstance();
                    DatabaseReference myRef_child = database_child.getReference("lamp"+String.valueOf(i));


                    myRef_child.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            //String value = dataSnapshot.getValue(String.class);

                            //Log.d(TAG, "Value is: " + value);
                            //Toast.makeText(StreetMap.this,map.get("ip").toString(),Toast.LENGTH_LONG).show();

                                /*ip[i]=Integer.valueOf(map.get("ip").toString());
                                lat[i]=Double.valueOf(map.get("lat").toString());
                                longi[i]=Double.valueOf(map.get("long").toString());
                                valuei = value;
                                child_load = 0;*/


                            LatLng lamp_loc = new LatLng(Double.valueOf(map.get("lat").toString()),Double.valueOf(map.get("long").toString()));
                            mMap.addMarker(new MarkerOptions().position(lamp_loc).title(map.get("ip").toString())
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapbulb2)));







                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            //Log.w(TAG, "Failed to read value.", error.toException());
                            Toast.makeText(StreetMap.this,"Failed to read lamp details",Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(StreetMap.this,"Failed to read lamp count",Toast.LENGTH_LONG).show();

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Toast.makeText(StreetMap.this,value.toString(),Toast.LENGTH_LONG).show();
       /**/
    }

    public void SignOut(View view) {

        mAuth.signOut();
        Context context = view.getContext();
        Intent act_main = new Intent(context, com.gyaanify.iot.smartcity.LoginActivity.class);
        context.startActivity(act_main);
        Toast.makeText(StreetMap.this, "You are Logged Out",Toast.LENGTH_SHORT).show();

    }

    int ldr_val=-1;

    public void OnESP(String lampno,String pinadd){

        String ipadd = "77";
        //if(Integer.parseInt(lampno)>3) {
        //    lampno = Integer.toString(3-Integer.parseInt(lampno));
        //    ipadd = "72";
        //}

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://"+ipgat+ipadd+"/?pin="+pinadd+"&lampno="+lampno,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(StreetMap.this,"Response: "+response,Toast.LENGTH_LONG).show();
                        ldr_val = Integer.parseInt(response);
                        //Toast.makeText(StreetMap.this,"Response: "+response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StreetMap.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        StringRequest stringRequest_weather = new StringRequest(Request.Method.POST,"https://api.openweathermap.org/data/2.5/weather?lat=28.52&lon=77.35&appid=875a24e2f254541a9cab0ef7693d5b1a",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(StreetMap.this,"Response: "+response,Toast.LENGTH_LONG).show();


                        try {

                            JSONObject reader = new JSONObject(response);

                            JSONObject main = new JSONObject(reader.getString("main"));
                            JSONObject clouds = new JSONObject(reader.getString("clouds"));

                            int temp_db = (int)Double.parseDouble(main.getString("temp"));
                            temp_db = temp_db/10;

                            String clouds_db = clouds.getString("all");
                            String vis_db = reader.getString("visibility");

                            //Toast toast = Toast.makeText(StreetMap.this,"Temp: "+main.getString("temp")+" Visibility: "+reader.getString("visibility")+" Cloud %: "+reader.getString("all"),Toast.LENGTH_LONG);
                            String weath = "Temp: "+temp_db+" °C | Cloud: "+clouds_db+"% | VIS: "+vis_db+" -> "+ldr_val;
                            Toast toast = Toast.makeText(StreetMap.this,weath,Toast.LENGTH_LONG);
                            toast.show();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("dataset");
                            Date curDate = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                            String DateToStr = format.format(curDate);

                            myRef.child(DateToStr).child("temp").setValue(temp_db);
                            myRef.child(DateToStr).child("clouds").setValue(clouds_db);
                            myRef.child(DateToStr).child("vis").setValue(vis_db);
                            myRef.child(DateToStr).child("ldr").setValue(ldr_val);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StreetMap.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){


        };

        RequestQueue requestQueue_weather = Volley.newRequestQueue(this);
        requestQueue_weather.add(stringRequest_weather);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        //LatLng sydney = new LatLng(32.718649, 74.868658);

        CameraPosition cp = new CameraPosition.Builder().target(new LatLng(28.522973, 77.358808))
                .zoom(15f)
                .bearing(315)
                .build();


        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
        mMap.setOnMarkerClickListener(this);



    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        final String ipadd = marker.getTitle();
        //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        Toast toast = Toast.makeText(StreetMap.this,"Lamp at "+ipgat+ipadd+" selected. Click CONTROL to view options.",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 350);
        toast.show();

        // Check if a click count was set, then display the click count.
        //Toast.makeText(StreetMap.this,ipadd,Toast.LENGTH_LONG).show();

        button1 = (Button) findViewById(R.id.btn_select);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(StreetMap.this, button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        OnESP(ipadd,item.getTitleCondensed().toString());
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return true;
    }

    public void TMRon(View view){

        OnESP("0","tmr_on");
    }

    public void TMRoff(View view){

        OnESP("0","tmr_off");
    }
}
