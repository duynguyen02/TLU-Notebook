package com.tianjun.tls_tkb.presentation.main.rooms_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tianjun.tls_tkb.R
import com.tianjun.tls_tkb.databinding.DialogAddRoomBinding
import com.tianjun.tls_tkb.databinding.FragmentRoomsManagementBinding
import com.tianjun.tls_tkb.domain.model.Room
import com.tianjun.tls_tkb.util.view.ViewHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomsManagementFragment : Fragment() {
    private lateinit var binding: FragmentRoomsManagementBinding
    private val roomsManagementViewModel: RoomsManagementViewModel by viewModels()
    private lateinit var roomAdapter: RoomAdapter
    private var isEditable: Boolean = false

    enum class Mode{
        ADD,
        UPDATE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomsManagementBinding.inflate(inflater, container, false)

        isEditable = roomsManagementViewModel.editable.value ?: false

        roomAdapter = RoomAdapter({
            roomsManagementViewModel.addSelectedRoom(it)
        },{
            roomsManagementViewModel.removeSelectedRoom(it)
        }){
            showRoomItemOption(it)
        }

        binding.roomMngFRvRooms.adapter = roomAdapter

        binding.roomMngBtnEdit.setOnClickListener { roomsManagementViewModel.changeEditable() }

        roomsManagementViewModel.getRooms().observe(viewLifecycleOwner) { rooms ->
            roomAdapter.submitList(rooms)
            binding.roomMngEtHeader.setOnClickListener {
                if (isEditable) {
                    rooms.forEach(roomsManagementViewModel::addSelectedRoom)
                }
            }
        }

        roomsManagementViewModel.selectedRooms.observe(viewLifecycleOwner) { selectedRooms ->
            roomAdapter.setSelectedItems(selectedRooms)
        }

        val addIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_24x24_black)
        val deleteIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_24x24_black)

        roomsManagementViewModel.editable.observe(viewLifecycleOwner) { editable ->
            binding.roomMngBtnEdit.text = if (editable) "Thêm" else "Sửa"
            binding.roomMngEtHeader.text = if (editable) "Chọn Tất Cả" else "QUẢN LÝ PHÒNG HỌC"
            binding.roomMngFFabAddRoom.setImageDrawable(if (editable) deleteIcon else addIcon)

            roomAdapter.setSelectMode(editable)

            binding.roomMngFFabAddRoom.setOnClickListener {
                if (editable) {
                    deleteRoomsDialogConfirm()
                } else {
                    openRoomInfoDiaLog(Mode.ADD, null)
                }
            }
            isEditable = editable
        }
        return binding.root
    }

    private fun deleteRoomsDialogConfirm() {
        if ((roomsManagementViewModel.selectedRooms.value?.size ?: 0) > 0){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Thông Báo")
                .setMessage("Bạn có muốn xóa các mục đã chọn?")
                .setPositiveButton(
                    "Có"
                ) { _, _ ->
                    roomsManagementViewModel.selectedRooms.value?.forEach(roomsManagementViewModel::deleteRoom)
                    roomsManagementViewModel.changeEditable()
                }
                .setNegativeButton(
                    "Không"
                ) { p0, _ ->
                    p0.dismiss()
                }
                .create()
                .show()
        }
        else{
            Toast.makeText(requireContext(), "Vui lòng chọn phòng cần xóa!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openRoomInfoDiaLog(mode : Mode, room: Room?) {
        val dialogBinding = DialogAddRoomBinding.inflate(layoutInflater)

        if(mode == Mode.UPDATE){
            dialogBinding.addRoomDEtRoomName.setText(room!!.name)
            dialogBinding.addRoomDEtRoonId.setText(room.id)
            dialogBinding.addRoomDEtRoonId.isEnabled = false
            dialogBinding.addRoomDBtnAdd.text = "Lưu"
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (mode == Mode.ADD) "Thêm phòng" else "Sửa phòng")
            .setView(dialogBinding.root)
            .create()

        dialogBinding.addRoomDBtnAdd.setOnClickListener {
            if (ViewHelper.isNotFill(
                    dialogBinding.addRoomDEtRoomName,
                    dialogBinding.addRoomDEtRoonId
                )
            ) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val finalRoom =  Room(
                dialogBinding.addRoomDEtRoonId.text.toString(),
                dialogBinding.addRoomDEtRoomName.text.toString()
            )
            if (mode == Mode.ADD){
                roomsManagementViewModel.addRoom(finalRoom)
                dialogBinding.addRoomDEtRoomName.setText("")
                dialogBinding.addRoomDEtRoonId.setText("")
            }
            else{
                roomsManagementViewModel.updateRoom(finalRoom)
            }
            Toast.makeText(requireContext(), "Done!", Toast.LENGTH_SHORT).show()
        }
        dialogBinding.addRoomDBtnExit.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun showRoomItemOption(room: Room) {
        val options = arrayOf("Vào phòng", "Chỉnh sửa thông tin phòng", "Xóa phòng")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Tùy chọn")
            .setItems(options) { _, which ->
            when (which) {
                0 -> {
                    TODO()
                }
                1 -> {
                    openRoomInfoDiaLog(Mode.UPDATE, room)
                }
                2 -> {
                    TODO()
                }
            }
        }.create().show()
    }

}