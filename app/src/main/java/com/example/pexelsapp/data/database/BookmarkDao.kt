package com.example.pexelsapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.domain.model.photo.Photo

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM photos")
    fun getAllBookmarks(): List<Photo>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getBookmarkById(id: Int): Photo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(photo: Photo)

    @Delete
    fun deleteBookmark(photo: Photo)

}