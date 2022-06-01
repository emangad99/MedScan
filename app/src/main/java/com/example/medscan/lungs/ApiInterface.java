package com.example.medscan.lungs;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Multipart
    @POST("xray")
    Call<Result> addImage(@Part MultipartBody.Part file);


}

