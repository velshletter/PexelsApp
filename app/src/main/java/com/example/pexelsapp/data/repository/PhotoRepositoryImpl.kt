package com.example.pexelsapp.data.repository

import android.net.Uri
import com.example.pexelsapp.data.ImageHandler
import com.example.pexelsapp.data.api.PhotoApi
import com.example.pexelsapp.domain.model.photo.PhotoResponse
import com.example.pexelsapp.domain.model.photo.SearchResponse
import com.example.pexelsapp.domain.repository.PhotoRepository
import retrofit2.Call
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: PhotoApi,
    private val imageHandler: ImageHandler
) : PhotoRepository {

    override fun getCuratedPhotos(): Call<SearchResponse> {
        return apiService.getCuratedPhotos()
    }

    override fun searchPhotos(searchTags: String): Call<SearchResponse> {
        return apiService.searchPhotos(query = searchTags)
    }

    override fun getPhotoById(id: Int): Call<PhotoResponse> {
        return apiService.getPhotoById(photoId = id)
    }

    override suspend fun downloadImage(url: String, photoId: Int): Uri? {
        return imageHandler.saveImageToGallery(url, photoId)
    }
}