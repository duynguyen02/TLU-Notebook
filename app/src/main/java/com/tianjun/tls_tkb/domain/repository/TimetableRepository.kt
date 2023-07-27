package com.tianjun.tls_tkb.domain.repository

import com.tianjun.tls_tkb.domain.model.Timetable

interface TimetableRepository {
    suspend fun addTimetables(timetables : List<Timetable>)
    suspend fun deleteAllTimetables()
}