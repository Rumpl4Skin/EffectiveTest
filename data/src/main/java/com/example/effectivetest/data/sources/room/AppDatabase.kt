package com.example.effectivetest.data.sources.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.effectivetest.data.model.room.AuthorEntity
import com.example.effectivetest.data.model.room.CourseEntity

@Database(entities = [CourseEntity::class, AuthorEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun authorDao(): AuthorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "course_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}