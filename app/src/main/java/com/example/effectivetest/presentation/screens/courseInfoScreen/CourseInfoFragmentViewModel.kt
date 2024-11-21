package com.example.effectivetest.presentation.screens.courseInfoScreen

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
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
    private val updateCourseDbUseCase: InsertOrUpdateCourseDbUseCase,
    private val getCourseByIdDbUseCase: GetCourseByIdDbUseCase,
    private val getCourseAuthorInfoUseCase: GetCourseAuthorInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UI.UiState())
    val uiState: StateFlow<UI.UiState> = _uiState.asStateFlow()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun setCourse(courseId: Long) {
        try {
            viewModelScope.launch {

                if (courseId.toInt() != 0) {
                    var course = Course()

                    course = getCourseByIdDbUseCase.execute(courseId = courseId) ?: Course()


                    if (course.author.photo.isEmpty()) {
                        viewModelScope.launch {
                            getAuthorInfo(authorId = course.author.id)
                        }

                    } else {
                        _uiState.update {
                            it.copy(course = course)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("TAG", it) }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getAuthorInfo(authorId: Long) {
        try {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        course = _uiState.value.course.copy(
                            author = getCourseAuthorInfoUseCase.execute(
                                authorId = authorId
                            )
                        )
                    )
                }
                updateCourseDbUseCase.execute(_uiState.value.course)
            }
        } catch (e: HttpException) {
            e.localizedMessage?.let { Log.e("TAG", it) }
        }
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