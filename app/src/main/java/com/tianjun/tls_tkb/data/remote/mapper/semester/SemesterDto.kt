package com.tianjun.tls_tkb.data.remote.mapper.semester


import com.google.gson.annotations.SerializedName

data class SemesterDto(
    @SerializedName("behaviorMarkEnd")
    val behaviorMarkEnd: Any?,
    @SerializedName("behaviorMarkStart")
    val behaviorMarkStart: Any?,
    @SerializedName("children")
    val children: Any?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("educationEnd")
    val educationEnd: Any?,
    @SerializedName("educationStart")
    val educationStart: Any?,
    @SerializedName("endDate")
    val endDate: Any?,
    @SerializedName("endRegisterDate")
    val endRegisterDate: Any?,
    @SerializedName("endRegisterDateString")
    val endRegisterDateString: Any?,
    @SerializedName("examRegisterPeriods")
    val examRegisterPeriods: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isCurrent")
    val isCurrent: Any?,
    @SerializedName("isLockRegister")
    val isLockRegister: Any?,
    @SerializedName("ordinalNumbers")
    val ordinalNumbers: Any?,
    @SerializedName("parent")
    val parent: Any?,
    @SerializedName("schoolYear")
    val schoolYear: Any?,
    @SerializedName("semesterCode")
    val semesterCode: String?,
    @SerializedName("semesterName")
    val semesterName: String?,
    @SerializedName("semesterRegisterPeriods")
    val semesterRegisterPeriods: Any?,
    @SerializedName("startDate")
    val startDate: Any?,
    @SerializedName("startRegisterDate")
    val startRegisterDate: Any?,
    @SerializedName("startRegisterDateString")
    val startRegisterDateString: Any?,
    @SerializedName("studentEnd")
    val studentEnd: Any?,
    @SerializedName("studentStart")
    val studentStart: Any?,
    @SerializedName("subSemesters")
    val subSemesters: Any?,
    @SerializedName("tuitionFeePerCredit")
    val tuitionFeePerCredit: Any?,
    @SerializedName("typeMarkRecognition")
    val typeMarkRecognition: Any?,
    @SerializedName("year")
    val year: Any?
)