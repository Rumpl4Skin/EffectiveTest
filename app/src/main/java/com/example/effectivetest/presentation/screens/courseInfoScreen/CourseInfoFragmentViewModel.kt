package com.example.effectivetest.presentation.screens.courseInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.useCases.GetCourseAuthorInfoUseCase
import com.example.effetivetest.domain.useCases.GetCourseByIdDbUseCase
import com.example.effetivetest.domain.useCases.InsertOrUpdateCourseDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseInfoFragmentViewModel @Inject constructor(
   // private val getCourseAuthorInfoUseCase: GetCourseAuthorInfoUseCase,
    private val updateCourseDbUseCase: InsertOrUpdateCourseDbUseCase,
    private val getCourseByIdDbUseCase: GetCourseByIdDbUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UI.UiState())
    val uiState: StateFlow<UI.UiState> = _uiState.asStateFlow()

    fun setCourse(course: Course) {
        if (course.author.photo.isEmpty()) {
            viewModelScope.launch {
                val courseDb = getCourseByIdDbUseCase.execute(course.author.id)
                if (courseDb != null && courseDb.author.photo.isNotEmpty()) {
                    _uiState.update {
                        it.copy(course = courseDb)
                    }
                } else {
                    getAuthorInfo(authorId = course.author.id)
                }
            }

        } else {
            _uiState.update {
                it.copy(course = course)
            }
        }
    }

    private fun getAuthorInfo(authorId: Long) {
        /*viewModelScope.launch {
            _uiState.update {
                it.copy(
                    course = _uiState.value.course.copy(
                        author = getCourseAuthorInfoUseCase.execute(
                            id = authorId
                        )
                    )
                )
            }
            updateCourseDbUseCase.execute(_uiState.value.course)
        }
*/
    }

    fun updateCourseFav() {
        viewModelScope.launch {
            val updatedCourse =
                _uiState.value.course.copy(isFavorite = !uiState.value.course.isFavorite)
            updateCourseDbUseCase.execute(updatedCourse)
            _uiState.update {
                it.copy(course = updatedCourse)
            }
        }
    }

    object UI {
        data class UiState(var course: Course = Course())
    }
}