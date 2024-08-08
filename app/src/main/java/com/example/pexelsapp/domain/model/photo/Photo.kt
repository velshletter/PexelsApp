package com.example.pexelsapp.domain.model.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey val id: Int,
    val width: Int,
    val height: Int,
    val photographer: String,
    val source: String,
)
