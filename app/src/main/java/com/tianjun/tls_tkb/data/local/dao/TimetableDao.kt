package com.tianjun.tls_tkb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tianjun.tls_tkb.data.local.entity.subject.TimeTableEnt

@Dao
interface TimetableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimeTables(timeTables : List<TimeTableEnt>)

    @Query("DELETE FROM timetableTable")
    suspend fun deleteAllTimetables()
}