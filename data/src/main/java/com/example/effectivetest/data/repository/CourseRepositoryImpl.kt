package com.example.effectivetest.data.repository

import android.util.Log
import com.example.effectivetest.data.remove.service.CourseRemoteService
import com.example.effetivetest.domain.model.CourseResponse
import com.example.effetivetest.domain.repository.CourseRepository
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val apiService: CourseRemoteService) :
    CourseRepository {

    override suspend fun getCourses(page: Int): CourseResponse {
        Log.e("TAG", "")
        return apiService.getCoursesListByPage(1)
    }
}
