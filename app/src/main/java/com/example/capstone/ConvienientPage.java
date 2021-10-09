package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConvienientPage extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager ().findFragmentById (R.id.map);
        mapFragment.getMapAsync ( this );
    }

    public void onMapReady(GoogleMap googleMap) {


        LatLng Myongji =new LatLng ( 37.222380, 127.187000);
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

        LatLng student=new LatLng ( 37.220000, 128.187017 );

        markerOptions.position(student);
        markerOptions.title("학생회관");
        markerOptions.snippet ("1층 :학생식당,명지카페,우체국" +
                "2층 : 여학우 휴게실 ");

        googleMap.addMarker (markerOptions);


        LatLng bokji=new LatLng ( 37.223010, 127.187010 );

        markerOptions.position(bokji);
        markerOptions.title("학생복지관");
        markerOptions.snippet ("1층 :하나은행 " +
                "2층 : 블루보틀(카페), 편의점 CU, 스테프핫도그,젤라또");

        googleMap.addMarker (markerOptions);

        LatLng mj=new LatLng ( 37.222000, 127.188500 );

        markerOptions.position(mj);
        markerOptions.title("명진당");
        markerOptions.snippet ("지하1층 :학생식당, GS25 (편의점),1층 : ATM 기기,"+"4층 : 복사실");

        googleMap.addMarker (markerOptions);


        LatLng changjo=new LatLng ( 37.222400, 127.189700 );

        markerOptions.position(changjo);
        markerOptions.title("창조관");
        markerOptions.snippet (" 1층 : ATM 기기 (하나) " +
                "4층 :파리바게트 (제과점) ");

        googleMap.addMarker (markerOptions);

        googleMap.setOnInfoWindowClickListener ( new GoogleMap.OnInfoWindowClickListener () {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),NewActivity.class);
                startActivity(intent);
            }
        } );

    }

}