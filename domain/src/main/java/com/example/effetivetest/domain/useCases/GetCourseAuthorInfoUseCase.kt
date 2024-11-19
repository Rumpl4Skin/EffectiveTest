package com.example.effetivetest.domain.useCases

import com.example.effetivetest.domain.model.Author
import com.example.effetivetest.domain.repository.CourseRepository

class GetCourseAuthorInfoUseCase(private val courseRepository: CourseRepository) {
    suspend fun execute(authorId: Long): Author {
        return courseRepository.getAuthor(authorId)
    }
}