package com.example.googlemapdemo.model;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.dao.ApartmentDAO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private EditText etMinPrice;
    private EditText etMaxPrice;
    private Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
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

        loadAllAptToMap();
        SearchByPrice();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Customize Window Info
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        initZoomView();
    }

    private void SearchByPrice() {
        etMinPrice = (EditText) findViewById(R.id.etMinPrice);
        etMaxPrice = (EditText) findViewById(R.id.etMaxPrice);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double MinPrice;
                double MaxPrice;
                if(etMinPrice.getText().toString().equals("")) {
                    MinPrice = 0;
                } else {
                    MinPrice = Double.parseDouble(etMinPrice.getText().toString());
                }

                if(etMaxPrice.getText().toString().equals("")) {
                    MaxPrice = 0;
                } else  {
                    MaxPrice = Double.parseDouble(etMaxPrice.getText().toString());
                }

                mMap.clear();
                initZoomView();

                ApartmentDAO apartmentDAO = new ApartmentDAO();
                List<Apartment> list = null;
                try {
                    list = apartmentDAO.findAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Apartment apt : list) {
                    if (apt.getPrice() >= MinPrice && apt.getPrice() <= MaxPrice) {
                        addMarkerOnMap(apt);
                    }
                }
            }
        });
    }

    public void loadAllAptToMap() {
        ApartmentDAO aptDao = new ApartmentDAO();
        try {
            List<Apartment> list = aptDao.findAll();
            for(Apartment apt : list) {
                addMarkerOnMap(apt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMarkerOnMap(Apartment apt) {
        LatLng latLng = new LatLng(apt.getLatitude(), apt.getLongtitue());
        MarkerOptions markerOptions = new MarkerOptions();
        /*markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.house_icon));*/
        markerOptions.position(latLng).title(String.valueOf(apt.getPrice()) + " $/Month")
                .snippet("ADDRESS: " + apt.getAddress() + "\n"
                        + "OWNER: " + apt.getOwner() + "\n"
                        + "PHONE: " + apt.getPhone());
        Marker marker = mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), (float) 17), 5000, null);
        return true;
    }

    public void initZoomView() {
        LatLng rootLatLng = new LatLng(21.027654, 105.787533);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rootLatLng, (float) 13.9), 5000, null);
    }
}