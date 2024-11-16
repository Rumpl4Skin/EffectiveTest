package com.example.effectivetest.data.remove.service

import com.example.effetivetest.domain.model.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CourseRemoteService {
    @GET("courses")
    suspend fun getCoursesListByPage(
        @Query("page") page: Int = 1
    ): CourseResponse
}