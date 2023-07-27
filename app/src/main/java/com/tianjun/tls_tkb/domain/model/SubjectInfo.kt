package com.tianjun.tls_tkb.domain.model

import com.tianjun.tls_tkb.data.remote.mapper.subject.TimetableDto

data class SubjectInfo(
    val id: Int?,
    val subjectName: String?,
    val numberOfCredit: Int?,
    val timetables: List<Timetable>?
)