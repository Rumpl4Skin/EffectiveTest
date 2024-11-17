package com.example.effectivetest.data.remove.service

import com.example.effectivetest.data.model.remove.NetworkCourseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseRemoteService {
    @GET("courses")
    suspend fun getCoursesListByPage(
        @Query("page") page: Int
    ): NetworkCourseResponse
}