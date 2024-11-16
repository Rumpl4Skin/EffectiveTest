package com.example.effectivetest.presentation.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivetest.data.model.CategoryFilter
import com.example.effectivetest.data.model.DifficultFilter
import com.example.effectivetest.data.model.PricingFilter
import com.example.effetivetest.domain.model.Course
import com.example.effetivetest.domain.useCases.GetCoursesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesListUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


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
            viewModelScope.launch {
                _uiState.update {
                    it.copy(courses = getCoursesUseCase.execute(1).courses)
                }
            }
    }

}

data class UiState(
    val filterPanelVisibility: Boolean = false,
    val categoryFilter: CategoryFilter = CategoryFilter.NONE,
    val difficultFilter: DifficultFilter = DifficultFilter.NONE,
    val pricingFilter: PricingFilter = PricingFilter.NONE,
    val courses: List<Course> = emptyList()
)

