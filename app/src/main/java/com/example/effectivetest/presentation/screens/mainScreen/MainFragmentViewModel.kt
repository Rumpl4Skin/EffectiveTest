package com.example.effectivetest.presentation.screens.mainScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainFragmentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun changeFilterPanelVisibility() {
        _uiState.value = _uiState.value.copy(filterPanelVisibility = !_uiState.value.filterPanelVisibility)
    }

}

data class UiState(
    val filterPanelVisibility: Boolean = false
)