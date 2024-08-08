package com.example.pexelsapp.domain.repository

import android.net.Uri
import com.example.pexelsapp.domain.model.photo.PhotoResponse
import com.example.pexelsapp.domain.model.photo.SearchResponse
import retrofit2.Call


interface PhotoRepository {
    fun getCuratedPhotos(): Call<SearchResponse>
    fun searchPhotos(searchTags: String): Call<SearchResponse>
    fun getPhotoById(id: Int): Call<PhotoResponse>
    suspend fun downloadImage(url: String, photoId: Int): Uri?
}