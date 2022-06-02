package com.example.medscan.skin;

import com.example.medscan.lungs.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SkinApiInterface {

    @Multipart
    @POST("skin")
    Call<SkinResult> addImage(@Part MultipartBody.Part file);
}
