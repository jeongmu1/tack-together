package com.dnlab.tack_together.activity_match;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dnlab.tack_together.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class MatchMainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private NaverMap naverMap;
    private MapView mapView;

    private Button matchFindButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);

        mapView = findViewById(R.id.matchMainMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(currentLatLng);
                    naverMap.moveCamera(cameraUpdate);
                }
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //임시로 액티비티 연결해놓음, 추후에 매칭하기 정보 컨텍스트 넘겨줘야함
        matchFindButton = findViewById(R.id.matchFindButton);
        matchFindButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), MatchMatchingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}