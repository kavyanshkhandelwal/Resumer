package com.example.resumer.data.repository

import com.example.resumer.data.model.ResumeResponse
import com.example.resumer.data.remote.ResumeApi
import android.util.Log
import javax.inject.Inject

class ResumeRepository @Inject constructor(
    private val api: ResumeApi
) {
    suspend fun fetchResume(userName: String): Result<ResumeResponse> {
        return try {
            val response = api.getResumeData(userName)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("ResumeRepo", "Network Error", e)
            Result.failure(e)
        }
    }
}