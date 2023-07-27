package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class SemesterSubjectDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("semester")
    val semester: SemesterDto?,
    @SerializedName("semesterName")
    val semesterName: Any?,
    @SerializedName("subject")
    val subject: SubjectDto?,
    @SerializedName("subjectName")
    val subjectName: Any?
)