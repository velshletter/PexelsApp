package com.example.pexelsapp.di

import android.content.Context
import com.example.pexelsapp.data.ImageHandler
import com.example.pexelsapp.data.api.CollectionApi
import com.example.pexelsapp.data.api.PhotoApi
import com.example.pexelsapp.data.database.BookmarkDao
import com.example.pexelsapp.data.repository.BookmarkRepositoryImpl
import com.example.pexelsapp.data.repository.CollectionRepositoryImpl
import com.example.pexelsapp.data.repository.PhotoRepositoryImpl
import com.example.pexelsapp.domain.repository.BookmarkRepository
import com.example.pexelsapp.domain.repository.CollectionRepository
import com.example.pexelsapp.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePhotoRepository(photoApi: PhotoApi, imageHandler: ImageHandler): PhotoRepository {
        return PhotoRepositoryImpl(photoApi, imageHandler)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(collectionApi: CollectionApi): CollectionRepository {
        return CollectionRepositoryImpl(collectionApi)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        bookmarkDao: BookmarkDao,
        imageHandler: ImageHandler
    ): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkDao, imageHandler)
    }

    @Provides
    @Singleton
    fun provideImageHandler(@ApplicationContext context: Context): ImageHandler {
        return ImageHandler(context)
    }
}