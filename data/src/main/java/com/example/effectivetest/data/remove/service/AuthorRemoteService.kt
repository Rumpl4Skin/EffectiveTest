package com.example.effectivetest.data.remove.service

import com.example.effectivetest.data.model.remove.NetworkAuthorResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthorRemoteService {
    @GET("users/{id}")
    suspend fun getAuthorById(
        @Path("id") id: Long
    ): NetworkAuthorResponse
}