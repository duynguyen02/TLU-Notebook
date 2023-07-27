package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class SubjectDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("numberOfCredit")
    val numberOfCredit: Int?,
    @SerializedName("subjectCode")
    val subjectCode: String?,
    @SerializedName("subjectName")
    val subjectName: String?,
    @SerializedName("subjectNameEng")
    val subjectNameEng: String?
)