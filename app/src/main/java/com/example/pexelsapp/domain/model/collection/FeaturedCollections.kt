package com.example.pexelsapp.domain.model.collection

import com.google.gson.annotations.SerializedName

data class FeaturedCollections(
    @SerializedName("collections") val collections: List<Collection>,
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("prev_page") val prevPage: String?
)
