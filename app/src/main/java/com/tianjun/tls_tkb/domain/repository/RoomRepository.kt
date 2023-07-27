package com.tianjun.tls_tkb.domain.repository

import com.tianjun.tls_tkb.data.local.entity.room.RoomEnt
import com.tianjun.tls_tkb.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getAllRooms() : Flow<List<Room>>
    fun getRoomById(id: String) : Flow<Room?>
    suspend fun addRoom(room : Room)
    suspend fun updateRoom(room: Room)
    suspend fun deleteRoom(room: Room)
}