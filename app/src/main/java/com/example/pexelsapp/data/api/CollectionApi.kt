package com.example.pexelsapp.data.api

import com.example.pexelsapp.AppConstants.API_KEY
import com.example.pexelsapp.domain.model.collection.FeaturedCollections
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CollectionApi {

    @GET("collections/featured")
    fun getFeaturedCollections(
        @Header("Authorization") apiKey: String = API_KEY   ,
        @Query("per_page") perPage: Int = 7
    ): Call<FeaturedCollections>

}