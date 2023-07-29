package com.tianjun.tls_tkb.data.local.repository

import com.tianjun.tls_tkb.data.local.dao.ScheduleDao
import com.tianjun.tls_tkb.data.local.entity.subject.ScheduleDto
import com.tianjun.tls_tkb.domain.model.Schedule
import com.tianjun.tls_tkb.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDao: ScheduleDao
) : ScheduleRepository {

    override suspend fun getCurrentSchedule(): List<Schedule> =
        scheduleDao.getCurrentScheduleByDayOfWeek(
            System.currentTimeMillis(), getDayOfWeek()
        ).map { ScheduleDto.dtoToSchedule(it) }


    override suspend fun getTomorrowSchedule(): List<Schedule> =
        scheduleDao.getCurrentScheduleByDayOfWeek(
            getTomorrowInMillis(), getTomorrowDayOfWeek()
        ).map { ScheduleDto.dtoToSchedule(it) }

    override suspend fun getWeekSchedule(): Map<Int, List<Schedule>> {
        val schedules = scheduleDao.getWeekSchedule()
        return schedules.map { ScheduleDto.dtoToSchedule(it) }
            .groupBy { it.weekIndex }
    }

    private fun getDayOfWeek() : Int{
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        return if (day == Calendar.SUNDAY) {
            day + 7
        } else {
            day
        }
    }

    private fun getTomorrowInMillis(): Long {
        val tomorrow = LocalDate.now().plusDays(1)
        return tomorrow.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    private fun getTomorrowDayOfWeek(): Int {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_WEEK, 1)
        }
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        return if (day == Calendar.SUNDAY) {
            day + 7
        } else {
            day
        }
    }

}