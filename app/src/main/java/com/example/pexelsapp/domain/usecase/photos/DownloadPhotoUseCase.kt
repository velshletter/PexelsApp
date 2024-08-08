package com.example.pexelsapp.domain.usecase.photos

import android.util.Log
import com.example.pexelsapp.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository,
) {
    suspend operator fun invoke(url: String, photoId: Int): String {
        return withContext(Dispatchers.IO) {
            val uri = repository.downloadImage(url, photoId)
            if (uri != null) {
                Log.d("MyLog", uri.toString())
                "Downloaded Successfully"
            } else "Something went wrong. Please try again"
        }
    }
}