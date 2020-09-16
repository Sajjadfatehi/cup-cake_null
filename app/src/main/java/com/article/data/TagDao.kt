package com.article.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by moha on 9/15/2020.
 */

@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTAg(tag: List<TagModel>)

    @Query("SELECT * FROM tags")
    fun getAllTag(): List<TagModel>
}