package com.tianjun.tls_tkb.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tianjun.tls_tkb.data.local.entity.room.RoomEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT EXISTS(SELECT 1 FROM roomTable WHERE id = :id)")
    fun checkIfIdExists(id: String): Boolean

    @Query("SELECT * FROM roomTable")
    fun getAllRooms(): Flow<List<RoomEnt>>

    @Query("SELECT * FROM roomTable WHERE id = :id")
    fun getRoomById(id: String): Flow<RoomEnt?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addRoom(room : RoomEnt)

    @Update
    fun updateRoom(roomEnt: RoomEnt)

    @Delete
    fun deleteRoom(roomEnt: RoomEnt)

    @Query("DELETE from roomTable")
    fun deleteAllRooms()
}