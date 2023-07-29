package com.tianjun.tls_tkb.data.local.entity.subject

import com.tianjun.tls_tkb.domain.model.Schedule

data class ScheduleDto(
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
) {


    companion object {

        fun listDtoToSchedules(dtos : List<ScheduleDto>) = dtos.map(::dtoToSchedule)

        fun dtoToSchedule(dto : ScheduleDto): Schedule {
            return Schedule(
                dto.timetableId,
                dto.roomName,
                dto.weekIndex,
                dto.startDate,
                dto.endDate,
                dto.startHourName,
                dto.startHourString,
                dto.startHourIndex,
                dto.endHourName,
                dto.endHourString,
                dto.endHourIndex,
                dto.subjectInfoId,
                dto.subjectName,
                dto.numberOfCredit
            )
        }

        fun scheduleToDto(schedule: Schedule): ScheduleDto {
            return ScheduleDto(
                schedule.timetableId,
                schedule.roomName,
                schedule.weekIndex,
                schedule.startDate,
                schedule.endDate,
                schedule.startHourName,
                schedule.startHourString,
                schedule.startHourIndex,
                schedule.endHourName,
                schedule.endHourString,
                schedule.endHourIndex,
                schedule.subjectInfoId,
                schedule.subjectName,
                schedule.numberOfCredit
            )
        }
    }
}
