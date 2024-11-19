package com.example.effetivetest.domain.useCases

import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effetivetest.domain.repository.CourseRepository

class GetCourseByIdDbUseCase(private val repository: CourseRepository) {
    suspend fun execute(id: Long): Course? {
        return repository.getCourseByIdDB(id = id)
    }
}