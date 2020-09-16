package com.core

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.core.db.AppDataBase

/**
 * Created by moha on 9/15/2020.
 */
object RoomDataBase{

    private var instance: AppDataBase? = null
    private const val DATABASE_NAME = "data_base"

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE articles ADD COLUMN rate INTEGER ")
        }
    }

    fun init(context: Context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                DATABASE_NAME
            ).addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build()
        }
    }

    fun getDB(): AppDataBase {
        return instance ?: throw RuntimeException("Database was null.")
    }
}