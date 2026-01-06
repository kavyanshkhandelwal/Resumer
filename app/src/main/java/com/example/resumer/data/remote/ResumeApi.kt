package com.example.resumer.data.remote

import com.example.resumer.data.model.ResumeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ResumeApi {
    @GET("resume")
    suspend fun getResumeData(
        @Query("name") name: String
    ): Response<ResumeResponse>
}