package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class SemesterDto(
    @SerializedName("description")
    val description: Any?,
    @SerializedName("endDate")
    val endDate: Long?,
    @SerializedName("endRegisterDate")
    val endRegisterDate: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isCurrent")
    val isCurrent: Boolean?,
    @SerializedName("isLockRegister")
    val isLockRegister: Any?,
    @SerializedName("ordinalNumbers")
    val ordinalNumbers: Int?,
    @SerializedName("semesterCode")
    val semesterCode: String?,
    @SerializedName("semesterName")
    val semesterName: String?,
    @SerializedName("startDate")
    val startDate: Long?,
    @SerializedName("startRegisterDate")
    val startRegisterDate: Any?,
    @SerializedName("tuitionFeePerCredit")
    val tuitionFeePerCredit: Any?,
    @SerializedName("year")
    val year: Any?
)