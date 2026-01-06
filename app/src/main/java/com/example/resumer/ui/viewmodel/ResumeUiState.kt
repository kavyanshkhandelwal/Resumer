package com.example.resumer.ui.viewmodel

import androidx.compose.ui.graphics.Color
import com.example.resumer.data.model.ResumeResponse


data class ResumeUiState(
    val isLoading: Boolean = false,
    val resumeData: ResumeResponse? = null,
    val errorMessage: String? = null,

    val fontSize: Float = 13f,
    val textColor: Color = Color.Black,
    val backgroundColor: Color = Color.White,
    val userLocation: String = "Locating..."
)