package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.geo.GeocodingResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPI {
    @GET("v2/geocode")
    Call<GeocodingResponseDTO> requestGeocoding(@Query("query") String query);

    @GET("v2/geocode")
    Call<GeocodingResponseDTO> requestGeocoding(@Query("query") String query,
                                                @Query("coordinate") String coordinate);
}
