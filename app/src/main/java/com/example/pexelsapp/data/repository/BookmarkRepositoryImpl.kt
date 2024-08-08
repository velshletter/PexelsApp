package com.example.pexelsapp.data.repository

import com.example.pexelsapp.data.ImageHandler
import com.example.pexelsapp.data.database.BookmarkDao
import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val imageHandler: ImageHandler
) : BookmarkRepository {
    override suspend fun getAllBookmarks(): List<Photo> {
        return bookmarkDao.getAllBookmarks()
    }

    override suspend fun addBookmark(photo: Photo) {
        val uri = imageHandler.saveImageToInternalStorage(photo.source, photo.photographer)
        if (uri != null) {
            val bookmarkPhoto = Photo(
                id = photo.id,
                width = photo.width,
                height = photo.height,
                photographer = photo.photographer,
                source = uri.toString()
            )
            bookmarkDao.addBookmark(bookmarkPhoto)
        }
    }

    override suspend fun deleteBookmark(photo: Photo) {
        bookmarkDao.deleteBookmark(photo)
    }

    override suspend fun getBookmarkById(id: Int): Photo? {
        return bookmarkDao.getBookmarkById(id)
    }
}