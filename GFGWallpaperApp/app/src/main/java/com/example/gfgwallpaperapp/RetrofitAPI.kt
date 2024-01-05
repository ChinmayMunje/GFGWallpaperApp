package com.example.gfgwallpaperapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitAPI {

    @Headers("Authorization: sL5zXvZSOEHzxqlHeVvbUzHmB0EgMIVBJ7yCtCfp2mvMa3ofJ803Q8pH")
    @GET("curated?per_page=30&page=1")
    fun getWallpapers(): Call<WallpaperRVModal>?


    @Headers("Authorization: sL5zXvZSOEHzxqlHeVvbUzHmB0EgMIVBJ7yCtCfp2mvMa3ofJ803Q8pH")
    @GET("search?")
    fun getWallpaperByCategory(
        @Query("query")  category:String,
        @Query("per_page")  per_page:Int,
        @Query("page")  page:Int,
    ): Call<WallpaperRVModal>?
}