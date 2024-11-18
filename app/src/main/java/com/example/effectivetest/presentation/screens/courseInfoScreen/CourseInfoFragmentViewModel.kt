package com.example.effectivetest.presentation.screens.courseInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effetivetest.domain.model.Author
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.useCases.GetCourseAuthorInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseInfoFragmentViewModel @Inject constructor(
    private val getCourseAuthorInfoUseCase: GetCourseAuthorInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UI.UiState())
    val uiState: StateFlow<UI.UiState> = _uiState.asStateFlow()

    fun setCourse(course: Course) {
        getAuthInfo(id = course.author.id)
        _uiState.update {
            it.copy(course = course)
        }
    }

    private fun getAuthInfo(id:Long){
        viewModelScope.launch {
            _uiState.update {
            it.copy(course = _uiState.value.course.copy(author = getCourseAuthorInfoUseCase.execute(id = id)))
            }
        }

    }

    object UI {
        data class UiState(var course: Course = Course())
    }
}