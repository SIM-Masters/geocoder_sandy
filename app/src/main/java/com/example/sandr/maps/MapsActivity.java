package com.example.sandr.maps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        Geocoder geocoder;
        List<Address> addresses;

        Intent recibir = getIntent();
        String lat = recibir.getStringExtra(String.valueOf(R.string.sendLatitud));
        String longi = recibir.getStringExtra(String.valueOf(R.string.sendLongitud));
        double latitude = Double.valueOf(lat).doubleValue();
        double longitude = Double.valueOf(longi).doubleValue();
        //latitud y longitud de Area definida
        double latDef = 20.641951, longDef = -103.451560;

        Location locationA = new Location(String.valueOf(R.string.pointA));

        locationA.setLatitude(latDef);
        locationA.setLongitude(longDef);

        Location locationB = new Location(String.valueOf(R.string.pointB));

        locationB.setLatitude(latitude);
        locationB.setLongitude(longitude);

        float distance = locationA.distanceTo(locationB);

        //comparar si la distancia es mayor al area definida (1000 m del punto de referencia)
        if(distance > 1000){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fuera), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.dentro), Toast.LENGTH_LONG).show();
        }


        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions().center(new LatLng(latDef, longDef)).radius(1000).fillColor(Color.argb(128, 255, 0, 0)); // In meters

        // Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);
        int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
        circle.setStrokeColor(strokeColor);


        // Add a marker in Sydney and move the camera
        LatLng ciateq = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(ciateq).title(getResources().getString(R.string.myLocation)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ciateq));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }


        //double latitude = 20.641951;
        //double longitude = -103.451560;
        try {
            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.yourLocation) + address + " " + city + " " + state + "\n" + country + " " + postalCode, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
