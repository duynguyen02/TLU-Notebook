package com.tianjun.tls_tkb.data.remote.mapper.semester


import com.google.gson.annotations.SerializedName

data class SchoolYearDto(
    @SerializedName("children")
    val children: Any?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("current")
    val current: Any?,
    @SerializedName("displayName")
    val displayName: Any?,
    @SerializedName("endDate")
    val endDate: Long?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isSemester")
    val isSemester: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("semesterId")
    val semesterId: Any?,
    @SerializedName("semesters")
    val semesters: Any?,
    @SerializedName("startDate")
    val startDate: Long?,
    @SerializedName("year")
    val year: Int?
)