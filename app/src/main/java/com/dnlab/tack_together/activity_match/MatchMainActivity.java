package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.activity_main.AddressApiActivity;
import com.dnlab.tack_together.api.dto.match.MatchRequestDTO;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

public class MatchMainActivity extends AppCompatActivity implements OnMapReadyCallback {
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationCallback locationCallback;
//    private LocationRequest locationRequest;

    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private MapView mapView;
    private LocationOverlay locationOverlay;
    private String myDestinationAddress;
    private LatLng myDestinationLocation;

    private Button matchFindButton, searchDestinationButton;
    private TextView searchedDestination;

    private static MatchMainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);

        instance = this;

        mapView = findViewById(R.id.matchMainMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource = new FusedLocationSource(this, 1000);

        searchDestinationButton = findViewById(R.id.searchDestination);
        searchDestinationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddressApiActivity.class);
            startActivityForResult(intent, 10000);
        });

        //임시로 액티비티 연결해놓음, 추후에 매칭하기 정보 컨텍스트 넘겨줘야함
        matchFindButton = findViewById(R.id.matchFindButton);
        matchFindButton.setOnClickListener(v-> {
            Intent intent = new Intent(getApplicationContext(), MatchMatchingActivity.class);
            intent.putExtra("requestInfo", new MatchRequestDTO("user1",
                    "user1",
                    "129.012175,35.151238",
                    "129.0006581,35.146861",
                    (short) 2,
                    (short) 2));
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case 10000:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data").substring(7);
                    if(data != null){
                        Log.i("주소검색", "data:"+data);
                        myDestinationAddress = data;
                    }
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //마커 띄우는 테스트 삭제예정
        Marker marker = new Marker();
        marker.setIcon(OverlayImage.fromResource(R.drawable.map_opponent_icon));
        marker.setPosition(new LatLng(35.1455966, 129.0091051));
        marker.setMap(naverMap);
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(MatchMainActivity.this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "홍길동";
            }
        });
        infoWindow.open(marker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
//        } else {
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public static void destroyActivity() {
        if (instance != null) {
            instance.finish();
            instance = null;
        }
    }
}