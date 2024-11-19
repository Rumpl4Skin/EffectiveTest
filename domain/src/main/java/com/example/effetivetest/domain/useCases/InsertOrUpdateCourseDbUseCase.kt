package com.example.effetivetest.domain.useCases

import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.repository.CourseRepository

class InsertOrUpdateCourseDbUseCase (private val repository: CourseRepository) {
    suspend fun execute(course: Course){
        repository.insertOrUpdateCourseDB(course = course)
    }
}