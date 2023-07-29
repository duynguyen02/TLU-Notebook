package com.tianjun.tls_tkb.presentation.main.schedule

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tianjun.tls_tkb.R
import com.tianjun.tls_tkb.databinding.FragmentScheduleBinding
import com.tianjun.tls_tkb.domain.model.Schedule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var weekScheduleAdapter: WeekScheduleAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        initComponents()
        observeSetup()
        scheduleViewModel.listSchedules(ScheduleViewModel.ScheduleType.TODAY)

        return binding.root
    }

    private fun initComponents() {
        scheduleAdapter = ScheduleAdapter()
        weekScheduleAdapter = WeekScheduleAdapter()
        binding.scheduleFRvSchedule.adapter = scheduleAdapter

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.schedule_option,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.scheduleFSprMode.adapter = adapter
        }

        binding.scheduleFSprMode.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(p2 == 3){
                        // TODO
                        Toast.makeText(requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
                    }
                    scheduleViewModel.listSchedules(ScheduleViewModel.ScheduleType.values()[p2])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    scheduleViewModel.listSchedules(ScheduleViewModel.ScheduleType.TODAY)
                }

            }

        binding.scheduleFCardViewQuote.setOnClickListener {
            scheduleViewModel.changeQuote()
        }

    }

    override fun onResume() {
        scheduleViewModel.listSchedules(
            ScheduleViewModel.ScheduleType.values()[binding.scheduleFSprMode.selectedItemPosition]
        )
        super.onResume()
    }

    private fun observeSetup() {
        quoteObserveSetup()
        schedulesObserveSetup()
        greetingObserveSetup()
    }

    private fun greetingObserveSetup() {

        scheduleViewModel.timeOfDay.observe(viewLifecycleOwner) {
            binding.scheduleFTvGreeting.text = it.getGreeting()
            binding.scheduleFTvGreeting.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(requireContext(), it.getIcon()),
                null, null, null
            )
        }
    }

    private fun quoteObserveSetup() {
        scheduleViewModel.quote.observe(viewLifecycleOwner) {
            binding.scheduleFTvQuote.text = it
        }
    }

    private fun schedulesObserveSetup() {
        scheduleViewModel.schedules.observe(viewLifecycleOwner) { result ->
            val hasSubjects = when (result) {
                is ScheduleViewModel.ScheduleResult.Schedules -> {
                    scheduleAdapter.submitList(result.schedules)
                    result.schedules.isNotEmpty()
                }

                is ScheduleViewModel.ScheduleResult.WeekSchedules -> {
                    val listOfPairs: List<Pair<Int, List<Schedule>>> =
                        result.weekSchedules.entries.map { entry ->
                            entry.key to entry.value
                        }
                    weekScheduleAdapter.submitList(listOfPairs)
                    listOfPairs.isNotEmpty()
                }

                is ScheduleViewModel.ScheduleResult.EmptySchedules -> false
            }

            binding.scheduleFCardLayoutNoSubjectsNotification.visibility =
                if (hasSubjects) View.GONE else View.VISIBLE

            binding.scheduleFRvSchedule.adapter = when (result) {
                is ScheduleViewModel.ScheduleResult.Schedules -> scheduleAdapter
                is ScheduleViewModel.ScheduleResult.WeekSchedules -> weekScheduleAdapter
                is ScheduleViewModel.ScheduleResult.EmptySchedules -> null
            }
        }
    }
}