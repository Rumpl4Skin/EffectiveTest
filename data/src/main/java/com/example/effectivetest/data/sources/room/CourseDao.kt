package com.example.effectivetest.data.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.effectivetest.data.model.room.CourseEntity

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses WHERE id = :id LIMIT 1")
    suspend fun getCourseById(id: Long): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: CourseEntity)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Transaction
    suspend fun insertOrUpdate(course: CourseEntity) {
        val existingCourse = getCourseById(course.id)
        if (existingCourse == null) {
            insertCourse(course)
        } else {
            updateCourse(course)
        }
    }
}