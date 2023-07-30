package com.tianjun.tls_tkb.data.local.repository

import com.tianjun.tls_tkb.data.local.dao.RoomDao
import com.tianjun.tls_tkb.data.local.entity.room.RoomEnt
import com.tianjun.tls_tkb.domain.model.Room
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao
) : RoomRepository {
    override fun getAllRooms(): Flow<List<Room>> = roomDao.getAllRooms().map {
        RoomEnt.listDtoToRoom(it)
    }

    override fun getRoomById(id: String): Flow<Room?> = roomDao.getRoomById(id).map {
        it?.let { roomEnt -> RoomEnt.dtoToRoom(roomEnt) }
    }

    override suspend fun isIdExists(id: String): Boolean = roomDao.checkIfIdExists(id)

    override suspend fun addRoom(room: Room) = roomDao.addRoom(RoomEnt.roomToDto(room))

    override suspend fun updateRoom(room: Room) = roomDao.updateRoom(RoomEnt.roomToDto(room))

    override suspend fun deleteRoom(room: Room) = roomDao.deleteRoom(RoomEnt.roomToDto(room))
    override suspend fun deleteAllRooms() = roomDao.deleteAllRooms()
}