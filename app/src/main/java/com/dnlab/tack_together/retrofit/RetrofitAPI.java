package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.auth.LoginRequestDTO;
import com.dnlab.tack_together.api.dto.auth.RefreshTokenRequestDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationRequestDTO;
import com.dnlab.tack_together.api.dto.auth.LoginResponseDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationResponseDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/api/auth/signUp")
    Call<RegistrationResponseDTO> signUp(@Body RegistrationRequestDTO registrationRequestDTO);

    @GET("/api/auth/checkUsername")
    Call<ResponseBody> checkUsernameDuplicated(@Query("username") String username);

    @POST("/api/auth/signIn")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("/api/auth/refreshToken")
    Call<LoginResponseDTO> refreshToken(@Body RefreshTokenRequestDTO refreshTokenRequestDTO);
}
