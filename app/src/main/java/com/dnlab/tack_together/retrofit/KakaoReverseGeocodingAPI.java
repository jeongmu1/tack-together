package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.reversegeo.KakaoReverseGeocodingResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KakaoReverseGeocodingAPI {
    @GET("geo/coord2address.json")
    Call<KakaoReverseGeocodingResponseDTO> requestReverseGeocoding(@Query("x") String x,
                                                                   @Query("y") String y);


}
