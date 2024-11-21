package com.example.effectivetest.data.di

import android.util.Log
import com.example.effetivetest.domain.Constants
import com.example.effectivetest.data.sources.remove.AuthorRemoteService
import com.example.effectivetest.data.repository.CourseRepositoryImpl
import com.example.effectivetest.data.sources.remove.CourseRemoteService
import com.example.effectivetest.data.sources.room.AuthorDao
import com.example.effectivetest.data.sources.room.CourseDao
import com.example.effetivetest.domain.repository.CourseRepository
import com.example.effetivetest.domain.useCases.GetCourseAuthorInfoUseCase
import com.example.effetivetest.domain.useCases.GetCoursesListByPageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
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
        val errorInterceptor = Interceptor { chain: Interceptor.Chain ->
            val response = chain.proceed(chain.request())
            if (!response.isSuccessful) {
                when (response.code) {
                    404 -> Log.e("TAG","404")
                    else -> Log.e("TAG","network")
                }
            }
            return@Interceptor response
        }
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(errorInterceptor).build()
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
        authorRemoteService: AuthorRemoteService,
        courseDao: CourseDao,
        authorDao: AuthorDao,
    ): CourseRepository {
        return CourseRepositoryImpl(
            coursesByPageService = courseListService,
            authorByIdService = authorRemoteService,
            courseDao = courseDao,
            authorDao = authorDao,
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

