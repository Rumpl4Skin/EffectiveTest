package com.example.effetivetest.domain.repository

import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.CourseResponse

interface CourseRepository {
    suspend fun getCoursesByPage(page: Int):CourseResponse

    suspend fun updateCourse(course: Course)
}