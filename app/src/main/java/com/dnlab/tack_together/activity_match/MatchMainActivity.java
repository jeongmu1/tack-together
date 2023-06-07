package com.dnlab.tack_together.activity_match;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dnlab.tack_together.R;
import com.dnlab.tack_together.activity_main.AddressApiActivity;
import com.dnlab.tack_together.api.dto.auth.MemberInfoResponseDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.geo.GeoRoadAddressDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.geo.KakaoGeoResponseDTO;
import com.dnlab.tack_together.api.dto.match.MatchRequestDTO;
import com.dnlab.tack_together.retrofit.AuthorizationAPI;
import com.dnlab.tack_together.retrofit.RetrofitBuilder;
import com.dnlab.tack_together.retrofit.kakaogeo.KakaoGeoAPI;
import com.dnlab.tack_together.retrofit.kakaogeo.KakaoGeoRetrofitBuilder;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MatchMainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private MapView mapView;
    private LatLng myDestinationLocation;
    private Marker destinationMarker;
    private static MatchMainActivity instance;
    private static final String TAG = "MatchMainActivity";
    private short originRange;
    private short destinationRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_main);

        instance = this;

        mapView = findViewById(R.id.matchMainMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        RadioGroup originRangeRadio = findViewById(R.id.originRange);
        originRangeRadio.setOnCheckedChangeListener(((group, checkedId) -> {
            Log.d(TAG, "originRangeRadio 변경 감지");
            RadioButton range = findViewById(checkedId);
            Log.d(TAG,"originRange TAG: " + (String) range.getTag());
            originRange = Short.parseShort((String)range.getTag());
        }));

        RadioGroup destinationRangeRadio = findViewById(R.id.destinationRange);
        destinationRangeRadio.setOnCheckedChangeListener(((group, checkedId) -> {
            Log.d(TAG, "destinationRangeRadio 변경 감지");
            RadioButton range = findViewById(checkedId);
            Log.d(TAG,"destinationRange TAG: " + (String) range.getTag());
            destinationRange = Short.parseShort((String)range.getTag());
        }));

        locationSource = new FusedLocationSource(this, 1000);

        Button searchDestinationButton = findViewById(R.id.searchDestination);
        searchDestinationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddressApiActivity.class);
            startActivityForResult(intent, 10000);
        });

        //임시로 액티비티 연결해놓음, 추후에 매칭하기 정보 컨텍스트 넘겨줘야함
        Button matchFindButton = findViewById(R.id.matchFindButton);
        matchFindButton.setOnClickListener(v-> {
            if (myDestinationLocation == null) {
                Toast.makeText(this, "목적지를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (originRange == 0) {
                Toast.makeText(this, "검색범위를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (destinationRange == 0) {
                Toast.makeText(this, "목적지 범위를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<MemberInfoResponseDTO> call = RetrofitBuilder.getInstance(getApplicationContext())
                    .getRetrofit()
                    .create(AuthorizationAPI.class)
                    .getMemberInfo();

            call.enqueue(new Callback<>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<MemberInfoResponseDTO> call, Response<MemberInfoResponseDTO> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Location origin = locationSource.getLastLocation();
                        assert origin != null;
                        String sOrigin = origin.getLongitude() + "," + origin.getLatitude();

                        String destination = myDestinationLocation.longitude + "," + myDestinationLocation.latitude;

                        Intent intent = new Intent(getApplicationContext(), MatchMatchingActivity.class);
                        intent.putExtra("requestInfo", new MatchRequestDTO(response.body().getUsername(),
                                response.body().getNickname(),
                                sOrigin,
                                destination,
                                originRange,
                                destinationRange));
                        startActivity(intent);
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<MemberInfoResponseDTO> call, Throwable t) {
                    Log.d(TAG, "통신실패");
                }
            });

        });

        destinationMarker = new Marker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 10000) {
            if (resultCode == RESULT_OK) {
                String data = intent.getExtras().getString("data").substring(7);
                Log.i("주소검색", "data:" + data);
                Call<KakaoGeoResponseDTO> call = KakaoGeoRetrofitBuilder.getInstance()
                        .getRetrofit()
                        .create(KakaoGeoAPI.class)
                        .requestGeocoding(data);

                call.enqueue(new Callback<>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<KakaoGeoResponseDTO> call, Response<KakaoGeoResponseDTO> response) {
                        if (response.isSuccessful()) {
                            KakaoGeoResponseDTO body = response.body();
                            assert body != null;

                            GeoRoadAddressDTO roadAddressDTO = body.getDocuments()
                                    .stream().findFirst().get()
                                    .getRoadAddress();
                            String lng = roadAddressDTO.getLongitude();
                            String lat = roadAddressDTO.getLatitude();

                            myDestinationLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            Log.d(TAG, "myDestinationLocation: " + myDestinationLocation);

                            naverMap.moveCamera(CameraUpdate.scrollTo(myDestinationLocation));
                            destinationMarker.setPosition(myDestinationLocation);
                            destinationMarker.setMap(naverMap);
                        }

                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<KakaoGeoResponseDTO> call, Throwable t) {

                    }
                });
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
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.setOnMapClickListener(((pointF, latLng) -> {
            myDestinationLocation = latLng;

            destinationMarker.setPosition(myDestinationLocation);
            destinationMarker.setMap(naverMap);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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