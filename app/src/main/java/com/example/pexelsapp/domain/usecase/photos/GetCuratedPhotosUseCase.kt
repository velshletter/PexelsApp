package com.example.pexelsapp.domain.usecase.photos


import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.photo.SearchResponse
import com.example.pexelsapp.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetCuratedPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend fun invoke(): ResponseState<SearchResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = photoRepository.getCuratedPhotos().execute()
                if (response.isSuccessful && response.body() != null) {
                    ResponseState.Success(response.body() as SearchResponse)
                } else ResponseState.Error(response.errorBody().toString())
            } catch (e: Exception) {
                ResponseState.Error(e.message.toString())
            }
        }
    }

}