package com.example.effectivetest.data.repository

import com.example.effetivetest.domain.Constants
import com.example.effectivetest.data.model.room.CourseEntity
import com.example.effectivetest.data.model.remove.NetworkAuthor
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effectivetest.data.model.remove.NetworkCourse
import com.example.effectivetest.data.model.remove.NetworkCourseResponse
import com.example.effectivetest.data.model.remove.NetworkMeta
import com.example.effectivetest.data.model.room.AuthorEntity
import com.example.effectivetest.data.sources.remove.AuthorRemoteService
import com.example.effectivetest.data.sources.remove.CourseRemoteService
import com.example.effectivetest.data.roundToDecimalPlaces
import com.example.effectivetest.data.sources.room.AuthorDao
import com.example.effectivetest.data.sources.room.CourseDao
import com.example.effetivetest.domain.model.Author
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.model.Meta
import com.example.effetivetest.domain.repository.CourseRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val coursesByPageService: CourseRemoteService,
    private val authorByIdService: AuthorRemoteService,
    private val courseDao: CourseDao,
    private val authorDao: AuthorDao
) :
    CourseRepository {
    override suspend fun getCoursesByPage(page: Int): CourseResponse {
        return coursesByPageService.getCoursesListByPage(page = page).toDomainModel()
    }


    override suspend fun getAuthor(id: Long): Author {
        return authorByIdService.getAuthorById(id = id).authors.map { it.toDomainModel() }.first()
    }

    override suspend fun insertOrUpdateCourseDB(course: Course) {
        val entity = course.toEntity()
        val existingAuthor = authorDao.getAuthorById(course.author.id)
        if (existingAuthor == null) {
            val author =
                authorByIdService.getAuthorById(id = entity.authorId).authors.first().toEntity()
            authorDao.insertAuthor(author)
        }
        courseDao.insertOrUpdate(entity)
    }

    override suspend fun getCourseByIdDB(id: Long): Course? {
        val course = courseDao.getCourseById(id)
        return authorDao.getAuthorById(course?.authorId ?: Constants.TEST_AUTHOR)?.toDomain()
            ?.let { course?.toDomain(it) }
    }

    private fun NetworkCourse.toDomainModel(): Course {
        return Course(
            id = this.id,
            title = this.title ?: "",
            description = this.description ?: "",
            cover = this.cover ?: "",
            updateDate = this.getNormalDateUpdate(),
            price = this.displayPrice ?: "-",
            isFavorite = this.isFavorite,
            rating = this.calculateRating().roundToDecimalPlaces(1),
            author = if (this.authors?.isNotEmpty() == true) this.authors.map { idAuth -> Author(id = idAuth) }
                .first()
            else Author(id = Constants.TEST_AUTHOR),
            actualLink = this.canonicalUrl ?: "",
            isActive = this.isActive
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
    private fun NetworkAuthor.toEntity(): AuthorEntity {
        return AuthorEntity(
            id = this.id,
            fullName = this.fullName,
            photo = this.photo
        )
    }

    private fun Course.toEntity(): CourseEntity {
        return CourseEntity(
            id = id,
            title = title,
            description = description,
            cover = cover,
            updateDate = updateDate,
            price = price,
            isFavorite = isFavorite,
            rating = rating,
            authorId = author.id,
            actualLink = actualLink,
            isActive = isActive,
        )
    }

    private fun CourseEntity.toDomain(currentCourseAuthor: Author): Course {
        return Course(
            id = id,
            title = title,
            description = description,
            cover = cover,
            updateDate = updateDate,
            price = price,
            isFavorite = isFavorite,
            rating = rating,
            author = currentCourseAuthor,
            actualLink = actualLink,
            isActive = isActive,
        )
    }

    private fun AuthorEntity.toDomain(): Author {
        return Author(id, fullName, photo)

    }
}
