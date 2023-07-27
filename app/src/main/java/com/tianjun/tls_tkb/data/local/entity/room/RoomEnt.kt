package com.tianjun.tls_tkb.data.local.entity.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.tianjun.tls_tkb.domain.model.Room

@Entity(tableName = "roomTable", primaryKeys = ["id"])
data class RoomEnt(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String
){
    companion object{
        fun listDtoToRoom(roomEnts : List<RoomEnt>) = roomEnts.map {
            dtoToRoom(it)
        }
        fun dtoToRoom(dto : RoomEnt) = Room(dto.id,dto.name)
        fun roomToDto(room: Room) = RoomEnt(room.id,room.name)
    }
}