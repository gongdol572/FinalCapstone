package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager ().findFragmentById (R.id.map);
        mapFragment.getMapAsync ( this );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng Myongji =new LatLng ( 37.222380, 127.187703);
        MarkerOptions markerOptions=new MarkerOptions ();
        markerOptions.position(Myongji);
        markerOptions.title("명지대학교");
        markerOptions.snippet ( "현재 나의 위치" );

        googleMap.addMarker (markerOptions);

        LatLng hambak=new LatLng ( 37.221095, 127.188615 );

        markerOptions.position(hambak);
        markerOptions.title("함박관");
        markerOptions.snippet ( "빈강의실 조회" );

        googleMap.addMarker (markerOptions);
        googleMap.moveCamera ( CameraUpdateFactory.newLatLngZoom ( Myongji,16 ) );


        googleMap.setOnInfoWindowClickListener ( new GoogleMap.OnInfoWindowClickListener () {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),NewActivity.class);
                startActivity(intent);
            }
        } );

    }

}
