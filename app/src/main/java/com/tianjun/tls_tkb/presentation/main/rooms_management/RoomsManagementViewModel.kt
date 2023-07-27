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

    /**
     * Toggles the 'editable' state and performs related actions based on the new state.
     * If the current state of 'editable' is true, it will be set to false, and vice versa.
     * Additionally, if the new 'editable' state is false, the list of selected rooms will be cleared.
     */
    fun changeEditable() {
        // Toggle the 'editable' state by negating its current value.
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