package com.example.effetivetest.domain.useCases

import com.example.effetivetest.domain.model.CourseResponse
import com.example.effetivetest.domain.repository.CourseRepository

class GetCoursesListByPageUseCase(private val repository: CourseRepository) {
    suspend fun execute(page: Int): CourseResponse {
        return repository.getCoursesByPage(page)
    }
}
