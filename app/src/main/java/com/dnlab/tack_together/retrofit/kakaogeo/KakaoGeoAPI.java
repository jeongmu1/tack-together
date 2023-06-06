package com.dnlab.tack_together.retrofit.kakaogeo;

import com.dnlab.tack_together.api.dto.kakaogeo.geo.KakaoGeoResponseDTO;
import com.dnlab.tack_together.api.dto.kakaogeo.reversegeo.KakaoReverseGeoResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KakaoGeoAPI {
    @GET("geo/coord2address.json")
    Call<KakaoReverseGeoResponseDTO> requestReverseGeocoding(@Query("x") String x,
                                                             @Query("y") String y);
@GET("search/address.json")
    Call<KakaoGeoResponseDTO> requestGeocoding(@Query("query") String query);
}
