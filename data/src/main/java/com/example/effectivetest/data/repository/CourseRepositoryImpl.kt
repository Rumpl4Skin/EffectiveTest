package com.example.effectivetest.data.repository

import android.content.Context
import com.example.data.R
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effectivetest.data.model.remove.NetworkCourse
import com.example.effectivetest.data.model.remove.NetworkCourseResponse
import com.example.effectivetest.data.model.remove.NetworkMeta
import com.example.effectivetest.data.remove.service.CourseRemoteService
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.Meta
import com.example.effetivetest.domain.repository.CourseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val apiService: CourseRemoteService) :
    CourseRepository {
    override suspend fun getCoursesByPage(page: Int): CourseResponse {
        return apiService.getCoursesListByPage(page = page).toDomainModel()
    }

    override suspend fun updateCourse(course: Course) {
        TODO("Not yet implemented")
    }

    private fun NetworkCourse.toDomainModel(): Course {
        return Course(
            id = this.id,
            title = this.title ?: "",
            description = this.description ?: "",
            cover = this.cover ?: "",
            updateDate = this.getNormalDateUpdate(),
            price = this.price?.toDoubleOrNull() ?: 0.0,
            isFavorite = this.isFavorite,
            rating = 0.0f,
            author = (authors?.firstOrNull() ?: "").toString(),
            actualLink = this.canonicalUrl ?: ""
        )
    }

    private fun NetworkMeta.toDomainModel(): Meta {
        return Meta(
            page = this.page,
            hasNext = this.hasNext,
            hasPrevious = this.hasPrevious
        )
    }

    private fun NetworkCourseResponse.toDomainModel(): CourseResponse {
        return CourseResponse(
            meta = this.meta.toDomainModel(),
            courses = this.courses.map { it.toDomainModel() }
        )
    }
}
