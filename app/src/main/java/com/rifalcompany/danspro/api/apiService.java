package com.rifalcompany.danspro.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService {
    @GET("positions.json")
    Call<List<jobModel>> getItems();

    @GET("positions.json")
    Call<List<jobModel>> getSingleItems(
            @Query("description") String desc,
            @Query("location") String loc);

    @GET("positions/{id}")
    Call<ResponseBody> getDetailItems(
            @Path("id") String id);

}