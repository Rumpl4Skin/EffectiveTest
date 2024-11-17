package com.example.effectivetest.presentation.screens.courseInfoScreen

import androidx.lifecycle.ViewModel
import com.example.effetivetest.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CourseInfoFragmentViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(UI.UiState())
    val uiState: StateFlow<UI.UiState> = _uiState.asStateFlow()

    fun setCourse(course: Course) {
        _uiState.update {
            it.copy(course = course)
        }
    }

    object UI {
        data class UiState(val course: Course? = null)
    }
}