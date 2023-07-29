package com.tianjun.tls_tkb.presentation.main.schedule


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tianjun.tls_tkb.databinding.ItemListviewSubjectBinding
import com.tianjun.tls_tkb.databinding.ItemListviewWeekSubjectsBinding
import com.tianjun.tls_tkb.domain.model.Schedule

class WeekScheduleAdapter : ListAdapter<Pair<Int, List<Schedule>>, WeekScheduleAdapter.ItemHolder>(ItemDiffCallback()){

    private val weekIndexes = listOf("THỨ HAI", "THỨ BA", "THỨ TƯ", "THỨ NĂM", "THỨ SÁU", "THỨ BẢY", "CHỦ NHẬT")

    class ItemDiffCallback : DiffUtil.ItemCallback<Pair<Int,List<Schedule>>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Int, List<Schedule>>,
            newItem: Pair<Int, List<Schedule>>
        ): Boolean = oldItem.first == newItem.first

        override fun areContentsTheSame(
            oldItem: Pair<Int, List<Schedule>>,
            newItem: Pair<Int, List<Schedule>>
        ): Boolean = oldItem.second == newItem.second

    }

    inner class ItemHolder(
        private val binding : ItemListviewWeekSubjectsBinding
        ) : RecyclerView.ViewHolder(binding.root){

        private lateinit var scheduleAdapter: ScheduleAdapter

        fun bind(daySchedules : Pair<Int, List<Schedule>>){

            scheduleAdapter = ScheduleAdapter()
            scheduleAdapter.submitList(daySchedules.second)

            binding.weekSubjectsIlvTvWeekIndex.text = weekIndexes[daySchedules.first - 2]
            binding.weekSubjectsIlvRvSchedules.adapter = scheduleAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ItemListviewWeekSubjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val daySchedules = getItem(position)
        holder.bind(daySchedules)
    }


}