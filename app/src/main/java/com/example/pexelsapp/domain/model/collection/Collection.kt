package com.example.pexelsapp.domain.model.collection

import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
)