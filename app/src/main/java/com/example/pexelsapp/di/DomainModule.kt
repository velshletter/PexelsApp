package com.example.pexelsapp.di

import com.example.pexelsapp.domain.repository.BookmarkRepository
import com.example.pexelsapp.domain.repository.CollectionRepository
import com.example.pexelsapp.domain.repository.PhotoRepository
import com.example.pexelsapp.domain.usecase.bookmarks.AddBookmarkUseCase
import com.example.pexelsapp.domain.usecase.bookmarks.GetAllBookmarksUseCase
import com.example.pexelsapp.domain.usecase.bookmarks.GetBookmarkByIdUseCase
import com.example.pexelsapp.domain.usecase.photos.GetCuratedPhotosUseCase
import com.example.pexelsapp.domain.usecase.collections.GetFeaturedCollectionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetCurPhotoUseCase(photoRepository: PhotoRepository): GetCuratedPhotosUseCase {
        return GetCuratedPhotosUseCase(photoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFeaturedCollectionsUseCase(collectionRepository: CollectionRepository): GetFeaturedCollectionsUseCase {
        return GetFeaturedCollectionsUseCase(collectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllBookmarksUseCase(bookmarkRepository: BookmarkRepository): GetAllBookmarksUseCase {
        return GetAllBookmarksUseCase(bookmarkRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetBookmarkByIdUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkByIdUseCase {
        return GetBookmarkByIdUseCase(bookmarkRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddBookmarkUseCase(bookmarkRepository: BookmarkRepository): AddBookmarkUseCase {
        return AddBookmarkUseCase(bookmarkRepository)
    }
}