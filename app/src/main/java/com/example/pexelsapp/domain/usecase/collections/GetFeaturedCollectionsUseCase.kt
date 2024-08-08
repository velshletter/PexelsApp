package com.example.pexelsapp.domain.usecase.collections

import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.collection.FeaturedCollections
import com.example.pexelsapp.domain.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFeaturedCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    suspend fun invoke(): ResponseState<FeaturedCollections> {
        return withContext(Dispatchers.IO) {
            try {
                val response = collectionRepository.getFeaturedCollections().execute()
                if (response.isSuccessful && response.body() != null) {
                    ResponseState.Success(response.body() as FeaturedCollections)
                } else ResponseState.Error(response.errorBody().toString())
            } catch (e: Exception) {
                ResponseState.Error(e.message.toString())
            }
        }
    }
}