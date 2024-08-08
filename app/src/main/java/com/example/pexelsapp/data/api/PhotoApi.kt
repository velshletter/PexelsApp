package com.example.pexelsapp.data.api

import com.example.pexelsapp.AppConstants.API_KEY
import com.example.pexelsapp.domain.model.photo.SearchResponse
import com.example.pexelsapp.domain.model.photo.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApi {

    @GET("curated")
    fun getCuratedPhotos(
        @Header("Authorization") apiKey: String = API_KEY,
        @Query("per_page") perPage: Int = 30
    ): Call<SearchResponse>

    @GET("search")
    fun searchPhotos(
        @Header("Authorization") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 30
    ): Call<SearchResponse>

    @GET("photos/{id}")
    fun getPhotoById(
        @Header("Authorization") apiKey: String = API_KEY,
        @Path("id") photoId: Int
    ): Call<PhotoResponse>

}