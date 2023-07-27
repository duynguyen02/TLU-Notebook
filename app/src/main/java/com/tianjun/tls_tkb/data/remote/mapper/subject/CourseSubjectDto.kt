package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class CourseSubjectDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("courseSubjectType")
    val courseSubjectType: Int?,
    @SerializedName("courseYearCode")
    val courseYearCode: String?,
    @SerializedName("displayName")
    val displayName: String?,
    @SerializedName("enrollmentClassCode")
    val enrollmentClassCode: Any?,
    @SerializedName("enrollmentClassId")
    val enrollmentClassId: Any?,
    @SerializedName("expanded")
    val expanded: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isDeniedAll")
    val isDeniedAll: Boolean?,
    @SerializedName("isGrantAll")
    val isGrantAll: Boolean?,
    @SerializedName("isOvelapTime")
    val isOvelapTime: Any?,
    @SerializedName("isSelected")
    val isSelected: Any?,
    @SerializedName("learningSkillId")
    val learningSkillId: Any?,
    @SerializedName("learningSkillName")
    val learningSkillName: Any?,
    @SerializedName("maxStudent")
    val maxStudent: Int?,
    @SerializedName("minStudent")
    val minStudent: Int?,
    @SerializedName("numberHours")
    val numberHours: Any?,
    @SerializedName("numberOfCredit")
    val numberOfCredit: Int?,
    @SerializedName("numberStudent")
    val numberStudent: Int?,
    @SerializedName("semesterSubject")
    val semesterSubject: SemesterSubjectDto?,
    @SerializedName("shortCode")
    val shortCode: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("subjectId")
    val subjectId: Any?,
    @SerializedName("teacher")
    val teacher: Any?,
    @SerializedName("timetables")
    val timetables: List<TimetableDto?>?
)