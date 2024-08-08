package com.example.pexelsapp.domain.repository

import com.example.pexelsapp.domain.model.collection.FeaturedCollections
import retrofit2.Call

interface CollectionRepository {
    fun getFeaturedCollections(): Call<FeaturedCollections>
}