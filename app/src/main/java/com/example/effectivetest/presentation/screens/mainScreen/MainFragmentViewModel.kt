package com.example.effectivetest.presentation.screens.mainScreen

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivetest.data.model.CategoryFilter
import com.example.effectivetest.data.model.DifficultFilter
import com.example.effectivetest.data.model.PricingFilter
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.useCases.GetCoursesListByPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesListByPageUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UI.UiState())
    val uiState: StateFlow<UI.UiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var hasNextPage = true

    init {
        getCoursesList()
    }

    fun startPagePos() {
        currentPage = 1
        hasNextPage = true

        _uiState.update {
            it.copy(newCourses = emptyList(), newItemLoad = 0)
        }
    }

    fun changeFilterPanelVisibility() {
        _uiState.value =
            _uiState.value.copy(filterPanelVisibility = !_uiState.value.filterPanelVisibility)
    }

    fun changeFilter(filter: CategoryFilter) {
        _uiState.value = _uiState.value.copy(categoryFilter = filter)
    }

    fun changeFilter(filter: DifficultFilter) {
        _uiState.value = _uiState.value.copy(difficultFilter = filter)
    }

    fun changeFilter(filter: PricingFilter) {
        _uiState.value = _uiState.value.copy(pricingFilter = filter)
    }

    fun getCoursesList() {
        if (hasNextPage) {
            viewModelScope.launch {
                try {
                    var response = getCoursesUseCase.execute(page = currentPage)

                    currentPage++
                    hasNextPage = response.meta.hasNext

                    if (response.courses.isEmpty()) { // если возвращается пустой список на этой странице, вызываем заново
                        getCoursesList()
                        return@launch
                    }

                    _uiState.update {
                        response = response.apply {
                            courses.filter { it.isActive }
                        }

                        val newCourses = response.apply {
                            courses.filter { course -> !_uiState.value.courses.contains(course) }
                        }.courses

                        _uiState.value.copy(
                            courses = (_uiState.value.courses + newCourses).distinctBy { it.id },//it.courses + response.courses,
                            newCourses = newCourses,
                            newItemLoad = newCourses.size
                        )
                    }
                } catch (e: Exception) { // Обработка ошибки
                    e.localizedMessage?.let { Log.e("TAB", "$it!!!!!!!!") }
                }
            }
        }
    }

    fun updCoursesWithoutRepetitions() {
        _uiState.update {
            _uiState.value.copy(
                courses = it.courses.filter { course -> !it.courses.contains(course) },
            )
        }

    }

    object UI {
        data class UiState(
            val filterPanelVisibility: Boolean = false,
            val categoryFilter: CategoryFilter = CategoryFilter.NONE,
            val difficultFilter: DifficultFilter = DifficultFilter.NONE,
            val pricingFilter: PricingFilter = PricingFilter.NONE,
            val courses: List<Course> = emptyList(),
            val newCourses: List<Course> = emptyList(),
            val newItemLoad: Int = 0,
        )
    }
}





