package com.example.effectivetest.presentation.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class ViewModelModule {

   /* @Provides
    fun provideMainScreenViewModel(getCoursesUseCase: GetCoursesListUseCase): MainFragmentViewModel {
        return MainFragmentViewModel(getCoursesUseCase)
    }
*/
}