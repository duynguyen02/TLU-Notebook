package com.tianjun.tls_tkb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tianjun.tls_tkb.data.local.entity.subject.ScheduleDto


@Dao
interface ScheduleDao {
    @Query(
        "SELECT \n" +
                "    tt.timetableId,\n" +
                "    tt.roomName,\n" +
                "    tt.weekIndex,\n" +
                "    tt.startDate,\n" +
                "    tt.endDate,\n" +
                "    tt.startHourName,\n" +
                "    tt.startHourString,\n" +
                "    tt.startHourIndex,\n" +
                "    tt.endHourName,\n" +
                "    tt.endHourString,\n" +
                "    tt.endHourIndex,\n" +
                "    tt.subjectInfoId,\n" +
                "    st.subjectName,\n" +
                "    st.numberOfCredit\n" +
                "FROM\n" +
                "    timetableTable AS tt\n" +
                "INNER JOIN\n" +
                "    subjectTable AS st ON tt.subjectInfoId = st.subjectInfoId\n" +
                "WHERE\n" +
                "    tt.startDate <= :currentMilliseconds\n" +
                "    AND tt.endDate >= :currentMilliseconds\n" +
                "    AND tt.weekIndex = :dayOfWeek\n" +
                "ORDER BY tt.startHourIndex\n" +
                ";"
    )
    fun getCurrentScheduleByDayOfWeek(currentMilliseconds: Long, dayOfWeek: Int): List<ScheduleDto>

    @Query(
        "WITH \n" +
                "\tmilliseconds_cte AS (\n" +
                "\t\tSELECT strftime('%s', 'now') * 1000 AS milliseconds\n" +
                "\t)\n" +
                "SELECT \n" +
                "    tt.timetableId,\n" +
                "    tt.roomName,\n" +
                "    tt.weekIndex,\n" +
                "    tt.startDate,\n" +
                "    tt.endDate,\n" +
                "    tt.startHourName,\n" +
                "    tt.startHourString,\n" +
                "    tt.startHourIndex,\n" +
                "    tt.endHourName,\n" +
                "    tt.endHourString,\n" +
                "    tt.endHourIndex,\n" +
                "    tt.subjectInfoId,\n" +
                "    st.subjectName,\n" +
                "    st.numberOfCredit\n" +
                "FROM\n" +
                "    timetableTable AS tt\n" +
                "INNER JOIN\n" +
                "    subjectTable AS st ON tt.subjectInfoId = st.subjectInfoId\n" +
                "WHERE\n" +
                "    tt.startDate <= (SELECT milliseconds FROM milliseconds_cte)\n" +
                "    AND tt.endDate >= (SELECT milliseconds FROM milliseconds_cte)\n" +
                "ORDER BY tt.weekIndex, tt.startHourIndex\n" +
                ";"
    )
    fun getWeekSchedule(): List<ScheduleDto>

}