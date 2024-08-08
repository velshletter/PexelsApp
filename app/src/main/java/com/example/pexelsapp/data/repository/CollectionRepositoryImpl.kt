package com.example.pexelsapp.data.repository

import com.example.pexelsapp.data.api.CollectionApi
import com.example.pexelsapp.domain.model.collection.FeaturedCollections
import com.example.pexelsapp.domain.repository.CollectionRepository
import retrofit2.Call
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionApi: CollectionApi
) : CollectionRepository {
    override fun getFeaturedCollections(): Call<FeaturedCollections> {
        return collectionApi.getFeaturedCollections()
    }
}