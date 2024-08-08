package com.example.pexelsapp.domain.repository

import com.example.pexelsapp.domain.model.photo.Photo

interface BookmarkRepository {
    suspend fun getAllBookmarks(): List<Photo>
    suspend fun addBookmark(photo: Photo)
    suspend fun deleteBookmark(photo: Photo)
    suspend fun getBookmarkById(id: Int): Photo?
}