package com.tianjun.tls_tkb.domain.repository

import com.tianjun.tls_tkb.domain.model.Schedule
import kotlinx.coroutines.flow.Flow


interface ScheduleRepository {
    suspend fun getCurrentSchedule() : List<Schedule>
    suspend fun getTomorrowSchedule() : List<Schedule>
    suspend fun getWeekSchedule() : Map<Int, List<Schedule>>
}