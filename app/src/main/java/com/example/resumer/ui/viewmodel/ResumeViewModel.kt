package com.example.resumer.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumer.data.repository.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val repository: ResumeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResumeUiState())
    val uiState: StateFlow<ResumeUiState> = _uiState.asStateFlow()

    init {
        fetchResumeData("Kavyansh Khandelwal")
    }

    fun fetchResumeData(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = repository.fetchResume(name)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        resumeData = result.getOrNull()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Unknown Error"
                    )
                }
            }
        }
    }

    //user actions
    fun updateFontSize(newSize: Float) {
        _uiState.update { it.copy(fontSize = newSize) }
    }

    fun updateTextColor(newColor: Color) {
        _uiState.update { it.copy(textColor = newColor) }
    }

    fun updateBackgroundColor(newColor: Color) {
        _uiState.update { it.copy(backgroundColor = newColor) }
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        val locString = "Lat: %.2f, Lng: %.2f".format(latitude, longitude)
        _uiState.update { it.copy(userLocation = locString) }
    }
}