package com.tianjun.tls_tkb.presentation.main.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tianjun.tls_tkb.databinding.ItemListviewSubjectBinding
import com.tianjun.tls_tkb.domain.model.Schedule

class ScheduleAdapter : ListAdapter<Schedule, ScheduleAdapter.ItemHolder>(ItemDiffCallback())  {

    class ItemDiffCallback : DiffUtil.ItemCallback<Schedule>() {
        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean = oldItem.timetableId == newItem.timetableId
        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean = oldItem == newItem
    }

    inner class ItemHolder(private val binding : ItemListviewSubjectBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(schedule: Schedule){
            binding.subjectIlvTvName.text = schedule.subjectName
            val period = "${schedule.startHourName} - ${schedule.endHourName}"
            binding.subjectIlvTvPeriod.text = period
            binding.subjectIlvTvRoom.text = schedule.roomName
            val time = "${schedule.startHourString} - ${schedule.endHourString}"
            binding.subjectIlvTvTime.text = time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ItemListviewSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val schedule = getItem(position)
        holder.bind(schedule)
    }

}