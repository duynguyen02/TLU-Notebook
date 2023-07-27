package com.tianjun.tls_tkb.data.remote.mapper.subject


import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("building")
    val building: Any?,
    @SerializedName("capacity")
    val capacity: Any?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("dupCode")
    val dupCode: Any?,
    @SerializedName("dupName")
    val dupName: Any?,
    @SerializedName("duplicate")
    val duplicate: Boolean?,
    @SerializedName("examCapacity")
    val examCapacity: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)