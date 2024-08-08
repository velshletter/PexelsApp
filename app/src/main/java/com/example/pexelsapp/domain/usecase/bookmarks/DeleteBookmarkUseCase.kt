package com.example.pexelsapp.domain.usecase.bookmarks

import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun invoke(photo: Photo) {
        withContext(Dispatchers.IO) {
            bookmarkRepository.deleteBookmark(photo)
        }
    }
}