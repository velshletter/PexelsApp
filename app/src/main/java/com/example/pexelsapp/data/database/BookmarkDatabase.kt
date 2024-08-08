package com.example.pexelsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pexelsapp.domain.model.photo.Photo

@Database(entities = [Photo::class], version = 1)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao
}