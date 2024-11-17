package com.example.effetivetest.domain.repository

import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.CourseResponse

interface CourseRepository {
    suspend fun getCourses(page: Int):CourseResponse
}