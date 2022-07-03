package com.example.medscan.kidneys;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface KidneyApiInterface {


    @POST("kidney")
    Call<KidneyResult> kidneyApi(@Body HashMap<String, String> body);

}
