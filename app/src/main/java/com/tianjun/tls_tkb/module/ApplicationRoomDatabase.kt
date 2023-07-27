package com.tianjun.tls_tkb.module

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tianjun.tls_tkb.data.local.dao.RoomDao
import com.tianjun.tls_tkb.data.local.dao.ScheduleDao
import com.tianjun.tls_tkb.data.local.dao.SubjectInfoDao
import com.tianjun.tls_tkb.data.local.dao.TimetableDao
import com.tianjun.tls_tkb.data.local.entity.room.RoomEnt
import com.tianjun.tls_tkb.data.local.entity.subject.SubjectInfoEnt
import com.tianjun.tls_tkb.data.local.entity.subject.TimeTableEnt

@Database(
    entities = [RoomEnt::class, SubjectInfoEnt::class, TimeTableEnt::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationRoomDatabase : RoomDatabase() {
    abstract fun scheduleDao() : ScheduleDao
    abstract fun roomDao() : RoomDao
    abstract fun subjectInfoDao() : SubjectInfoDao
    abstract fun timetableDao() : TimetableDao
}