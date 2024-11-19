package com.example.effectivetest.data.di

import android.content.Context
import com.example.effectivetest.data.repository.CourseRepositoryImpl
import com.example.effectivetest.data.sources.room.AppDatabase
import com.example.effectivetest.data.sources.room.AuthorDao
import com.example.effectivetest.data.sources.room.CourseDao
import com.example.effetivetest.domain.repository.CourseRepository
import com.example.effetivetest.domain.useCases.GetCourseByIdDbUseCase
import com.example.effetivetest.domain.useCases.InsertOrUpdateCourseDbUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideAuthorDao(appDatabase: AppDatabase): AuthorDao {
        return appDatabase.authorDao()
    }

    @Provides
    fun provideCourseDao(appDatabase: AppDatabase): CourseDao {
        return appDatabase.courseDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideInsertOrUpdateCourseDbUseCase(repository: CourseRepository):InsertOrUpdateCourseDbUseCase{
        return InsertOrUpdateCourseDbUseCase(repository = repository)
    }

    @Provides
    fun provideGetCourseByIdDbUseCase(repository: CourseRepository):GetCourseByIdDbUseCase{
        return GetCourseByIdDbUseCase(repository = repository)
    }

}