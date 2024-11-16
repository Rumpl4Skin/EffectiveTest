package com.example.effetivetest.domain.useCases

import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effetivetest.domain.repository.CourseRepository

class GetCoursesListUseCase(private val repository: CourseRepository) {
    suspend fun execute(page: Int): CourseResponse {
        return repository.getCourses(page)
    }
}
