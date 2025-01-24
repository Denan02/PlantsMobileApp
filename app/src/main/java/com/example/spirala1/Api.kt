package com.example.spirala1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("{id}/")
    suspend fun getBiljkeLatinski(@Path("id") id: String, @Query("token") apiKey: String, @Query("filter[scientific_name]") upit: String): Response<VraceneBiljke>
    @GET("plants/{id}/")
    suspend fun getBiljkeID(@Path("id") id: Int, @Query("token") apiKey: String): Response<SveDetaljnijeBiljke>
    @GET("plants/search/")
    suspend fun getBiljkeSubString(@Query("token") apiKey: String,@Query("q") q : String, @Query("filter[flower_color]") boja: String): Response<VraceneBiljke>
}