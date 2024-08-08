package com.example.pexelsapp.domain.usecase.bookmarks

import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBookmarkByIdUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun invoke(id: Int): ResponseState<Photo> {
        return withContext(Dispatchers.IO) {
            val photo = bookmarkRepository.getBookmarkById(id)
            if (photo != null) {
                ResponseState.Success(photo)
            } else ResponseState.Error("Cant find an Image")
        }
    }
}