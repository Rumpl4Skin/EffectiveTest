package com.example.effetivetest.domain.repository

import com.example.effetivetest.domain.model.Author
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.CourseResponse

interface CourseRepository {
    suspend fun getCoursesByPage(page: Int):CourseResponse

    suspend fun getAuthor(id:Long): Author

    suspend fun insertOrUpdateCourseDB(course: Course)

    suspend fun getCourseByIdDB(id: Long): Course?
}