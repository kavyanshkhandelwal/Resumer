package com.example.resumer.data.model
import com.google.gson.annotations.SerializedName

data class ResumeResponse(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("twitter") val twitter: String,
    @SerializedName("address") val address: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("skills") val skills: List<String>,
    @SerializedName("projects") val projects: List<Project>
)

data class Project(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String
)