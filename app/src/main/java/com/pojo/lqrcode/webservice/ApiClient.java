package com.pojo.lqrcode.webservice;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pojo.lqrcode.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getAPIClient() {

        if (retrofit == null) {

            OkHttpClient okHttpClient =   new OkHttpClient.Builder()
                    /*.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original=chain.request();
                            Request.Builder requestBuilder=original.newBuilder()
                                    .addHeader("Authorization",AUTH)
                                    .addHeader("Authorization","Basic " + token)
                                    .method(original.method(),original.body());
                            Request request=requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })*/
                    .retryOnConnectionFailure(true)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();


            retrofit = new Retrofit.Builder().baseUrl(AppConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


}