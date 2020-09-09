package com.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by moha on 9/6/2020.
 */
@Entity(tableName = "user_table")
data class UserDataBase(
    @PrimaryKey(autoGenerate = true) var userId: Long = 0L,
    @ColumnInfo(name = "to_article")
    var article_id: Long,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "phone_number") var phoneNumber: String?
)
