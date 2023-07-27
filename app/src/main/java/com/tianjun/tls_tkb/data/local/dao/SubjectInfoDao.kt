package com.tianjun.tls_tkb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tianjun.tls_tkb.data.local.entity.subject.SubjectInfoEnt

@Dao
interface SubjectInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubjectsInfo(subjects : List<SubjectInfoEnt>)

    @Query("DELETE FROM subjectTable")
    suspend fun deleteAllSubjects()
}