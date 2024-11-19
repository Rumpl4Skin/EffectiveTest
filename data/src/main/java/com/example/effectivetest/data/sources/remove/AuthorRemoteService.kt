package com.example.effectivetest.data.sources.remove

import com.example.effectivetest.data.model.remove.NetworkAuthorResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthorRemoteService {
    @GET("users/{id}")
    suspend fun getAuthorById(
        @Path("id") id: Long
    ): NetworkAuthorResponse
}