package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.reversegeo.ReverseGeocodingResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReverseGeocodingAPI {
    @GET
    Call<ReverseGeocodingResponseDTO> requestReverseGeocoding(@Query("coords") String coords);
}
