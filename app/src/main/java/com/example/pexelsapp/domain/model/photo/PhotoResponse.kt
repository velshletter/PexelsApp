package com.example.pexelsapp.domain.model.photo

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("photographer") val photographer: String,
    @SerializedName("src") val src: PhotoSrc
){
    data class PhotoSrc(
        @SerializedName("original") val original: String,
        @SerializedName("medium") val medium: String
    )
}