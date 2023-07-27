package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class StartHourDto(
    @SerializedName("end")
    val end: Any?,
    @SerializedName("endString")
    val endString: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("indexNumber")
    val indexNumber: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("start")
    val start: Long?,
    @SerializedName("startString")
    val startString: String?,
    @SerializedName("type")
    val type: Any?
)