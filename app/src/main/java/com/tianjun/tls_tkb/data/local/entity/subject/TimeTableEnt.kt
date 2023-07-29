package com.tianjun.tls_tkb.data.local.entity.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.tianjun.tls_tkb.domain.model.Timetable


@Entity(
    tableName = "timetableTable", primaryKeys = ["timetableId"],
    indices = [Index(value = ["subjectInfoId"])],
    foreignKeys = [
        ForeignKey(
            entity = SubjectInfoEnt::class,
            parentColumns = ["subjectInfoId"],
            childColumns = ["subjectInfoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TimeTableEnt(
    @ColumnInfo(name = "timetableId")
    val timetableId: Int?,
    @ColumnInfo(name = "roomName")
    val roomName: String?,
    @ColumnInfo(name = "weekIndex")
    val weekIndex: Int?,
    @ColumnInfo(name = "startDate")
    val startDate: Long?,
    @ColumnInfo(name = "endDate")
    val endDate: Long?,
    @ColumnInfo(name = "startHourName")
    val startHourName: String?,
    @ColumnInfo(name = "startHourString")
    val startHourString: String?,
    @ColumnInfo(name = "startHourIndex")
    val startHourIndex: Int?,
    @ColumnInfo(name = "endHourName")
    val endHourName: String?,
    @ColumnInfo(name = "endHourString")
    val endHourString: String?,
    @ColumnInfo(name = "endHourIndex")
    val endHourIndex: Int?,
    @ColumnInfo(name = "subjectInfoId")
    val subjectInfoId: Int?
) {
    companion object {
        fun timetableToEnt(timetable: Timetable) = TimeTableEnt(
            timetable.id,
            timetable.roomName,
            timetable.weekIndex,
            timetable.startDate,
            timetable.endDate,
            timetable.startHourName,
            timetable.startHourString,
            timetable.startHourIndex,
            timetable.endHourName,
            timetable.endHourString,
            timetable.endHourIndex,
            timetable.subjectInfoId
        )
    }
}