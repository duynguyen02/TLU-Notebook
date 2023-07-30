package com.tianjun.tls_tkb.presentation.main.rooms_management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tianjun.tls_tkb.domain.model.Room
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomsManagementViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _editable = MutableLiveData(false)
    val editable: LiveData<Boolean>
        get() = _editable

    private val _selectedRooms = MutableLiveData(HashSet<Room>())
    val selectedRooms : LiveData<HashSet<Room>>
        get() = _selectedRooms

    fun changeEditable() {
        _editable.value = !(_editable.value!!)
        _selectedRooms.value?.clear()
    }

    fun removeSelectedRoom(room: Room){
        _selectedRooms.value?.remove(room)
        _selectedRooms.postValue(_selectedRooms.value)
    }

    fun addSelectedRoom(room: Room){
        _selectedRooms.value?.add(room)
        _selectedRooms.postValue(_selectedRooms.value)
    }


    fun getRooms() = roomRepository.getAllRooms().asLiveData()
    fun isIdExists(id : String) : Boolean{
        val isExists = runBlocking {
            val result = withContext(Dispatchers.IO) {
                roomRepository.isIdExists(id)
            }
            result
        }
        return isExists
    }
    fun addRoom(room: Room) {
        viewModelScope.launch(Dispatchers.IO){
            roomRepository.addRoom(room)
        }
    }
    fun updateRoom(room: Room) {
        viewModelScope.launch(Dispatchers.IO){
            roomRepository.updateRoom(room)
        }
    }
    fun deleteRoom(room: Room) {
        viewModelScope.launch(Dispatchers.IO){
            roomRepository.deleteRoom(room)
        }
    }
}