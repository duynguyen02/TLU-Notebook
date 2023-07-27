package com.tianjun.tls_tkb.domain.model

data class Timetable(
    val id: Int?,
    val roomName: String?,
    val startDate: Long?,
    val endDate: Long?,
    val startHourName : String?,
    val startHourString : String?,
    val startHourIndex : Int?,
    val endHourName : String?,
    val endHourString : String?,
    val endHourIndex : Int?,
    val subjectInfoId : Int?
)
