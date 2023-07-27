package com.tianjun.tls_tkb.data.remote.mapper.semester


import com.google.gson.annotations.SerializedName

data class ExamRegisterPeriodDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("createDate")
    val createDate: Any?,
    @SerializedName("createdBy")
    val createdBy: Any?,
    @SerializedName("end")
    val end: Long?,
    @SerializedName("examRooms")
    val examRooms: Any?,
    @SerializedName("fromDate")
    val fromDate: Long?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isPublished")
    val isPublished: Boolean?,
    @SerializedName("modifiedBy")
    val modifiedBy: Any?,
    @SerializedName("modifyDate")
    val modifyDate: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("registerPeriod")
    val registerPeriod: Any?,
    @SerializedName("semester")
    val semester: SemesterDto?,
    @SerializedName("semesterSubjectExams")
    val semesterSubjectExams: Any?,
    @SerializedName("start")
    val start: Long?,
    @SerializedName("toDate")
    val toDate: Long?,
    @SerializedName("voided")
    val voided: Boolean?
)