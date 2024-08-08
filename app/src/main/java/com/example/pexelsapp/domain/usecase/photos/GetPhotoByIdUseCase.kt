package com.example.pexelsapp.domain.usecase.photos

import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.model.photo.PhotoResponse
import com.example.pexelsapp.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend fun invoke(photoId: Int): ResponseState<Photo> {
        return withContext(Dispatchers.IO) {
            try {
                val response = photoRepository.getPhotoById(photoId).execute()
                if (response.isSuccessful && response.body() != null) {
                    val photoResponse = response.body() as PhotoResponse
                    val photo = Photo(
                        id = photoResponse.id,
                        width = photoResponse.width,
                        height = photoResponse.height,
                        photographer = photoResponse.photographer,
                        source = photoResponse.src.original
                    )
                    ResponseState.Success(photo)
                } else ResponseState.Error(response.message().toString())
            } catch (e: Exception) {
                ResponseState.Error(e.message.toString())
            }
        }
    }

}