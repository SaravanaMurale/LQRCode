package com.pojo.lqrcode.webservice;



import com.pojo.lqrcode.model.BaseResponse;
import com.pojo.lqrcode.model.UserDetailsResponse;
import com.pojo.lqrcode.model.UserEnterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("api/qrscan/")
    Call<UserDetailsResponse> doGetUserDetails(@Query("qrcode") String qrcode);

    @POST("api/userdetails/add/")
    Call<BaseResponse> doSubmitUserEnteredDetails(@Body UserEnterRequest userEnterRequest);

}
