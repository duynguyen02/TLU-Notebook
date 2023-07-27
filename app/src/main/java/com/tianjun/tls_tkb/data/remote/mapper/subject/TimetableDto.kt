package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class TimetableDto(
    @SerializedName("assistantStaffCode")
    val assistantStaffCode: Any?,
    @SerializedName("assistantTeacher")
    val assistantTeacher: Any?,
    @SerializedName("courseHourseEndCode")
    val courseHourseEndCode: Any?,
    @SerializedName("courseHourseStartCode")
    val courseHourseStartCode: Any?,
    @SerializedName("courseSubjectId")
    val courseSubjectId: Int?,
    @SerializedName("end")
    val end: Any?,
    @SerializedName("endDate")
    val endDate: Long?,
    @SerializedName("endHour")
    val endHour: EndHourDto?,
    @SerializedName("fromWeek")
    val fromWeek: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("numberHours")
    val numberHours: Any?,
    @SerializedName("room")
    val room: RoomDto?,
    @SerializedName("roomCode")
    val roomCode: Any?,
    @SerializedName("roomName")
    val roomName: String?,
    @SerializedName("staffCode")
    val staffCode: Any?,
    @SerializedName("start")
    val start: Any?,
    @SerializedName("startDate")
    val startDate: Long?,
    @SerializedName("startHour")
    val startHour: StartHourDto?,
    @SerializedName("teacher")
    val teacher: Any?,
    @SerializedName("teacherName")
    val teacherName: Any?,
    @SerializedName("toWeek")
    val toWeek: Int?,
    @SerializedName("weekIndex")
    val weekIndex: Int?
)