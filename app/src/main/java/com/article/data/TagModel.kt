package com.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags"
)
data class TagModel(
    @PrimaryKey
    @ColumnInfo(name = "tag") val title: String

) {


}

