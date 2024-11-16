package com.example.effectivetest.presentation.di

import com.example.effectivetest.presentation.screens.mainScreen.MainFragmentViewModel
import com.example.effetivetest.domain.useCases.GetCoursesListUseCase
import dagger.Module
import dagger.Provides
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