package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName
import com.tianjun.tls_tkb.domain.model.SubjectInfo
import com.tianjun.tls_tkb.domain.model.Timetable

data class SubjectInfoDto(
    @SerializedName("basicTuitionFee")
    val basicTuitionFee: Any?,
    @SerializedName("courseSubject")
    val courseSubject: CourseSubjectDto?,
    @SerializedName("createDate")
    val createDate: String?,
    @SerializedName("discountPercent")
    val discountPercent: Any?,
    @SerializedName("discountValue")
    val discountValue: Any?,
    @SerializedName("examStatus")
    val examStatus: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isMainSpec")
    val isMainSpec: Any?,
    @SerializedName("isParent")
    val isParent: Boolean?,
    @SerializedName("message")
    val message: Any?,
    @SerializedName("numberOfCredit")
    val numberOfCredit: Int?,
    @SerializedName("regType")
    val regType: Int?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("studentName")
    val studentName: Any?,
    @SerializedName("studyTime")
    val studyTime: Any?,
    @SerializedName("subjectCode")
    val subjectCode: String?,
    @SerializedName("subjectId")
    val subjectId: Int?,
    @SerializedName("subjectName")
    val subjectName: String?,
    @SerializedName("subjectStatus")
    val subjectStatus: Any?,
    @SerializedName("totalCredit")
    val totalCredit: Any?,
    @SerializedName("totalFee")
    val totalFee: Any?,
    @SerializedName("tuitionFee")
    val tuitionFee: Any?,
    @SerializedName("tuitionFeePerCredit")
    val tuitionFeePerCredit: Any?,
    @SerializedName("typeRegister")
    val typeRegister: Int?
) {
    companion object {
        fun dtoToSubjectInfo(dto: SubjectInfoDto) = SubjectInfo(
            dto.id,
            dto.subjectName,
            dto.numberOfCredit,
            dto.courseSubject?.timetables?.map {
                Timetable(
                    it?.id,
                    it?.roomName,
                    it?.startDate,
                    it?.endDate,
                    it?.startHour?.name,
                    it?.startHour?.startString,
                    it?.startHour?.indexNumber,
                    it?.endHour?.name,
                    it?.endHour?.endString,
                    it?.endHour?.indexNumber,
                    dto.id
                )
            }
        )
    }
}