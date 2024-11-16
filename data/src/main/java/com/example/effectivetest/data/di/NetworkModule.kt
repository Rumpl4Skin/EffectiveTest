package com.example.effectivetest.data.di

import com.example.effectivetest.data.Constants
import com.example.effectivetest.data.repository.CourseRepositoryImpl
import com.example.effectivetest.data.remove.service.CourseRemoteService
import com.example.effetivetest.domain.repository.CourseRepository
import com.example.effetivetest.domain.useCases.GetCoursesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY // Вы можете использовать также BASIC, HEADERS, или NONE
        }
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideApiService(json: Json, okHttpClient: OkHttpClient): CourseRemoteService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(CourseRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideCourseRepository(courseListService: CourseRemoteService): CourseRepository {
        return CourseRepositoryImpl(courseListService)
    }

    @Provides
    @Singleton
    fun provideGetCoursesListUseCase(repository: CourseRepository): GetCoursesListUseCase {
        return GetCoursesListUseCase(repository)
    }


}

