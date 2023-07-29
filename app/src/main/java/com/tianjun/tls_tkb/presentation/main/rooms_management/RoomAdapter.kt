package com.tianjun.tls_tkb.presentation.main.rooms_management

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tianjun.tls_tkb.databinding.ItemListviewRoomBinding
import com.tianjun.tls_tkb.domain.model.Room
import javax.inject.Inject

class RoomAdapter @Inject constructor(
    private val onItemSelectedListener: ((room: Room) -> Unit)?,
    private val onItemUnSelectedListener: ((room: Room) -> Unit)?,
    private val onItemClickListener: ((room: Room) -> Unit)?
) : ListAdapter<Room, RoomAdapter.ItemHolder>(ItemDiffCallback()) {

    private var selectMode = false
    private var selectedRooms = HashSet<Room>()

    class ItemDiffCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem == newItem
    }

    inner class ItemHolder(private val binding: ItemListviewRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {

            binding.root.setOnClickListener {
                if (selectMode) {
                    if (selectedRooms.contains(room)) {
                        onItemUnSelectedListener?.let { it1 -> it1(room) }
                        binding.root.setCardBackgroundColor(Color.WHITE)
                    } else {
                        onItemSelectedListener?.let { it1 -> it1(room) }
                        binding.root.setCardBackgroundColor(Color.LTGRAY)
                    }
                } else {
                    onItemClickListener?.let { it1 -> it1(room) }
                }
            }

            if (selectMode && selectedRooms.contains(room)) {
                binding.root.setCardBackgroundColor(Color.LTGRAY)
            } else {
                binding.root.setCardBackgroundColor(Color.WHITE)
            }

            binding.roomLiTvId.text = room.id
            binding.roomLiTvName.text = room.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            ItemListviewRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val room = getItem(position)
        holder.bind(room)
    }

    fun setSelectMode(active: Boolean) {
        selectMode = active
        val selectedIndexes = selectedRooms.map { currentList.indexOf(it) }
        if (!selectMode) {
            selectedRooms.clear()
            selectedIndexes.forEach { notifyItemChanged(it) }
        }
    }

    fun setSelectedItems(selectedSet: Set<Room>) {
        selectedRooms = HashSet(selectedSet)
        selectedRooms.forEach {
            val position = currentList.indexOf(it)
            notifyItemChanged(position)
        }
    }

}