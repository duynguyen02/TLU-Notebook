package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class EndHourDto(
    @SerializedName("end")
    val end: Long?,
    @SerializedName("endString")
    val endString: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("indexNumber")
    val indexNumber: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("start")
    val start: Any?,
    @SerializedName("startString")
    val startString: Any?,
    @SerializedName("type")
    val type: Any?
)