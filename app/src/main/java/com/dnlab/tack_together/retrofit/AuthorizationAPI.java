package com.dnlab.tack_together.retrofit;

import com.dnlab.tack_together.api.dto.auth.CheckUsernameResponseDTO;
import com.dnlab.tack_together.api.dto.auth.LoginRequestDTO;
import com.dnlab.tack_together.api.dto.auth.MemberInfoResponseDTO;
import com.dnlab.tack_together.api.dto.auth.MemberUpdateDTO;
import com.dnlab.tack_together.api.dto.auth.RefreshTokenRequestDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationRequestDTO;
import com.dnlab.tack_together.api.dto.auth.LoginResponseDTO;
import com.dnlab.tack_together.api.dto.auth.RegistrationResponseDTO;
import com.dnlab.tack_together.api.dto.auth.TestTokenResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthorizationAPI {
    @POST("/api/auth/sign-up")
    Call<RegistrationResponseDTO> signUp(@Body RegistrationRequestDTO registrationRequestDTO);

    @POST("/api/auth/sign-in")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO loginRequestDTO);

    @POST("/api/auth/refresh-token")
    Call<LoginResponseDTO> refreshToken(@Body RefreshTokenRequestDTO refreshTokenRequestDTO);

    @GET("/api/auth/check-username")
    Call<CheckUsernameResponseDTO> checkUsernameDuplicated(@Query("username") String username);

    @GET("/api/auth/test-auth")
    Call<TestTokenResponseDTO> testAuthentication();

    @GET("/api/auth/member-info")
    Call<MemberInfoResponseDTO> getMemberInfo();

    @PUT("/api/auth/update-info")
    Call<MemberUpdateDTO> updateMemberInfo(@Body MemberUpdateDTO memberUpdateDTO);
}
