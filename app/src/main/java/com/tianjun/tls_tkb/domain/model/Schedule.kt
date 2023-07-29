package com.tianjun.tls_tkb.domain.model

data class Schedule(
    val timetableId: Int,
    val roomName: String,
    val weekIndex: Int,
    val startDate: Long,
    val endDate: Long,
    val startHourName: String,
    val startHourString: String,
    val startHourIndex: Int,
    val endHourName: String,
    val endHourString: String,
    val endHourIndex: Int,
    val subjectInfoId: Int,
    val subjectName: String,
    val numberOfCredit: Int
)