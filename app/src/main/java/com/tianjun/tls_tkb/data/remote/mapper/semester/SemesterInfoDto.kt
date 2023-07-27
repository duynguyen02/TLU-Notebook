package com.tianjun.tls_tkb.data.remote.mapper.semester


import com.google.gson.annotations.SerializedName
import com.tianjun.tls_tkb.domain.model.SemesterInfo

data class SemesterInfoDto(
    @SerializedName("behaviorMarkEnd")
    val behaviorMarkEnd: Any?,
    @SerializedName("behaviorMarkStart")
    val behaviorMarkStart: Any?,
    @SerializedName("children")
    val children: List<Any>?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("educationEnd")
    val educationEnd: Any?,
    @SerializedName("educationStart")
    val educationStart: Any?,
    @SerializedName("endDate")
    val endDate: Long?,
    @SerializedName("endRegisterDate")
    val endRegisterDate: Any?,
    @SerializedName("endRegisterDateString")
    val endRegisterDateString: Any?,
    @SerializedName("examRegisterPeriods")
    val examRegisterPeriods: List<ExamRegisterPeriodDto>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isCurrent")
    val isCurrent: Boolean?,
    @SerializedName("isLockRegister")
    val isLockRegister: Any?,
    @SerializedName("ordinalNumbers")
    val ordinalNumbers: Int?,
    @SerializedName("parent")
    val parent: Any?,
    @SerializedName("schoolYear")
    val schoolYear: SchoolYearDto?,
    @SerializedName("semesterCode")
    val semesterCode: String?,
    @SerializedName("semesterName")
    val semesterName: String?,
    @SerializedName("semesterRegisterPeriods")
    val semesterRegisterPeriods: List<SemesterRegisterPeriodDto>?,
    @SerializedName("startDate")
    val startDate: Long?,
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
){
    companion object{
        fun dtoToSemesterInfo(dto : SemesterInfoDto) = SemesterInfo(
            dto.id,
            dto.semesterName
        )
    }
}