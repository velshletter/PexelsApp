package com.example.pexelsapp.di

import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.database.BookmarkDao
import com.example.pexelsapp.data.database.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookmarkDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = BookmarkDatabase::class.java,
            name = "BookmarkDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarksDao(database: BookmarkDatabase): BookmarkDao {
        return database.getBookmarkDao()
    }
}