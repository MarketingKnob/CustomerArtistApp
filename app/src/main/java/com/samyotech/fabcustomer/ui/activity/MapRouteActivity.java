package com.samyotech.fabcustomer.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.ui.DrawRoute.DrawMarker;
import com.samyotech.fabcustomer.ui.DrawRoute.DrawRouteMaps;

public class MapRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double srcLat=0.0,srcLng=0.0,destLat=0.0,destLng=0.0;
    private static final String TAG = "MapRouteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);

        Intent intent = getIntent();
        if (intent!=null){

            srcLat      =intent.getDoubleExtra("SrcLat",0.0);
            srcLng      =intent.getDoubleExtra("SrcLng",0.0);
            destLat     =intent.getDoubleExtra("DesLat",0.0);
            destLng     =intent.getDoubleExtra("DesLng",0.0);

            Log.d(TAG, "onCreate: "+srcLat+","+srcLng+","+destLat+","+destLng);

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng origin = new LatLng(srcLat, srcLng);
        LatLng destination = new LatLng(destLat, destLng);
        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.marker_a, "Origin Location");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.marker_b, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 15));

    }
}
