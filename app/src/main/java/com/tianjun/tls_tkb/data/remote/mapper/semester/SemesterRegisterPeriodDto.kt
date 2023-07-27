package com.tianjun.tls_tkb.data.remote.mapper.semester


import com.google.gson.annotations.SerializedName

data class SemesterRegisterPeriodDto(
    @SerializedName("createDate")
    val createDate: Any?,
    @SerializedName("createdBy")
    val createdBy: Any?,
    @SerializedName("displayOrder")
    val displayOrder: Int?,
    @SerializedName("endRegisterTime")
    val endRegisterTime: Any?,
    @SerializedName("endRegisterTimeString")
    val endRegisterTimeString: Any?,
    @SerializedName("endUnRegisterTime")
    val endUnRegisterTime: Any?,
    @SerializedName("endUnRegisterTimeString")
    val endUnRegisterTimeString: Any?,
    @SerializedName("examPeriods")
    val examPeriods: List<Any>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isLockRegister")
    val isLockRegister: Any?,
    @SerializedName("modifiedBy")
    val modifiedBy: Any?,
    @SerializedName("modifyDate")
    val modifyDate: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("semester")
    val semester: SemesterDtoX?,
    @SerializedName("startRegisterTime")
    val startRegisterTime: Any?,
    @SerializedName("startRegisterTimeString")
    val startRegisterTimeString: Any?,
    @SerializedName("voided")
    val voided: Boolean?
)