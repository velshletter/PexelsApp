package com.example.pexelsapp.domain.model.photo

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("photos") val photoResponses: List<PhotoResponse>,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("prev_page") val prevPage: String?
)
