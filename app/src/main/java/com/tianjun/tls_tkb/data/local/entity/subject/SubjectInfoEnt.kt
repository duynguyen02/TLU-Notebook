package com.tianjun.tls_tkb.data.local.entity.subject

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.tianjun.tls_tkb.domain.model.SubjectInfo
import java.io.Serializable


@Entity(tableName = "subjectTable", primaryKeys = ["subjectInfoId"])
data class SubjectInfoEnt(
    @ColumnInfo(name = "subjectInfoId")
    val subjectInfoId: Int?,
    @ColumnInfo(name = "subjectName")
    val subjectName: String?,
    @ColumnInfo(name = "numberOfCredit")
    val numberOfCredit: Int?,
) : Serializable {
    companion object{
        fun subjectInfoToEnt(subjectInfo: SubjectInfo) = SubjectInfoEnt(
            subjectInfo.id,
            subjectInfo.subjectName,
            subjectInfo.numberOfCredit
        )
    }
}