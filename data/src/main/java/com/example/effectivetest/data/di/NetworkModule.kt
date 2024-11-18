package com.example.effectivetest.data.di

import android.content.Context
import com.example.effectivetest.data.Constants
import com.example.effectivetest.data.remove.service.AuthorRemoteService
import com.example.effectivetest.data.repository.CourseRepositoryImpl
import com.example.effectivetest.data.remove.service.CourseRemoteService
import com.example.effetivetest.domain.repository.CourseRepository
import com.example.effetivetest.domain.useCases.GetCourseAuthorInfoUseCase
import com.example.effetivetest.domain.useCases.GetCoursesListByPageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CourseRemoteService {
        return retrofit.create(CourseRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorRemoteService(retrofit: Retrofit): AuthorRemoteService {
        return retrofit.create(AuthorRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideCourseRepository(
        courseListService: CourseRemoteService,
        authorRemoteService: AuthorRemoteService
    ): CourseRepository {
        return CourseRepositoryImpl(
            coursesByPageService = courseListService,
            authorByIdService = authorRemoteService
        )
    }

    @Provides
    @Singleton
    fun provideGetCoursesListUseCase(repository: CourseRepository): GetCoursesListByPageUseCase {
        return GetCoursesListByPageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCourseAuthorInfoUseCase(repository: CourseRepository): GetCourseAuthorInfoUseCase {
        return GetCourseAuthorInfoUseCase(repository)
    }


}

