package com.faradhy.storyusersapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faradhy.storyusersapp.data.response.RowItemStory

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<RowItemStory>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, RowItemStory>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}