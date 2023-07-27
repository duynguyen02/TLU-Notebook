package com.tianjun.tls_tkb.data.local.repository

import com.tianjun.tls_tkb.data.local.dao.TimetableDao
import com.tianjun.tls_tkb.data.local.entity.subject.TimeTableEnt
import com.tianjun.tls_tkb.domain.model.Timetable
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(
    private val timetableDao: TimetableDao
) : TimetableRepository {

    override suspend fun addTimetables(timetables: List<Timetable>) = timetableDao.addTimeTables(timetables.map { TimeTableEnt.timetableToEnt(it) })

    override suspend fun deleteAllTimetables() = timetableDao.deleteAllTimetables()
}