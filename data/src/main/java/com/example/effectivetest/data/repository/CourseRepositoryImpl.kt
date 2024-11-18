package com.example.effectivetest.data.repository

import com.example.effectivetest.data.model.remove.NetworkAuthor
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effectivetest.data.model.remove.NetworkCourse
import com.example.effectivetest.data.model.remove.NetworkCourseResponse
import com.example.effectivetest.data.model.remove.NetworkMeta
import com.example.effectivetest.data.remove.service.AuthorRemoteService
import com.example.effectivetest.data.remove.service.CourseRemoteService
import com.example.effetivetest.domain.model.Author
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.Meta
import com.example.effetivetest.domain.repository.CourseRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val coursesByPageService: CourseRemoteService,
    private val authorByIdService: AuthorRemoteService
) :
    CourseRepository {
    override suspend fun getCoursesByPage(page: Int): CourseResponse {
        return coursesByPageService.getCoursesListByPage(page = page).toDomainModel()
    }

    override suspend fun updateCourse(course: Course) {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthor(id: Long): Author {
        return authorByIdService.getAuthorById(id = id).authors.map { it.toDomainModel() }.first()
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
            author = if (this.authors?.isNotEmpty() == true) this.authors.map { idAuth-> Author(id = idAuth) }
                .first()
            else Author(id = 0),
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

    private fun NetworkAuthor.toDomainModel(): Author {
        return Author(
            id = this.id,
            fullName = this.fullName,
            photo = this.photo
        )
    }
}
