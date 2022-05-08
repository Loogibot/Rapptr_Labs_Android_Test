package com.datechnologies.androidtest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginData {

    Call<ResponseBody> sendLoginData(
            @Field("email") String email,
            @Field("password") String password
    );
}