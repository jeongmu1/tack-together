package com.dnlab.tack_together.api;

import com.dnlab.tack_together.api.dto.request.RequestLogin;
import com.dnlab.tack_together.api.dto.request.RequestRegistration;
import com.dnlab.tack_together.api.dto.response.ResponseLogin;
import com.dnlab.tack_together.api.dto.response.ResponseRegistration;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/api/auth/signUp")
    Call<ResponseRegistration> signUp(@Body RequestRegistration requestRegistration);

    @GET("/api/auth/checkUsername")
    Call<ResponseBody> checkUsernameDuplicated(@Query("username") String username);

    @POST("/api/auth/signIn")
    Call<ResponseLogin> login(@Body RequestLogin requestLogin);
}
