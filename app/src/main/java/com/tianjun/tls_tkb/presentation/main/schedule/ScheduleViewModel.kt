package com.tianjun.tls_tkb.presentation.main.schedule

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tianjun.tls_tkb.R
import com.tianjun.tls_tkb.domain.model.Schedule
import com.tianjun.tls_tkb.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    @ApplicationContext context : Context,
    private val scheduleRepository: ScheduleRepository
) : ViewModel(){

    sealed class ScheduleResult{
        object EmptySchedules : ScheduleResult()
        class Schedules(
            val schedules : List<Schedule>
        ) : ScheduleResult()
        class WeekSchedules(
            val weekSchedules: Map<Int, List<Schedule>>
        ) : ScheduleResult()
    }

    enum class ScheduleType{
        TODAY,
        TOMORROW,
        WEEK,
        ALL
    }

    enum class TimeOfDay {
        MORNING {
            override fun getGreeting(): String = "Chào buổi sáng!"
            override fun getIcon(): Int = R.drawable.ic_sunrise_24x24_red

        },
        NOON {
            override fun getGreeting(): String = "Chào buổi trưa!"
            override fun getIcon(): Int = R.drawable.ic_noon_24x24_yellow
        },
        AFTERNOON {
            override fun getGreeting(): String = "Chào buổi chiều!"
            override fun getIcon(): Int = R.drawable.ic_afternoom_24x24_orange
        },
        EVENING {
            override fun getGreeting(): String = "Chào buổi tối!"
            override fun getIcon(): Int = R.drawable.ic_moon_24x24_yellow
        };

        abstract fun getGreeting(): String
        abstract fun getIcon() : Int

        companion object {
            fun from(currentTime : LocalTime): TimeOfDay = when {
                currentTime.isBefore(LocalTime.NOON) -> MORNING
                currentTime.isBefore(LocalTime.of(13, 0)) -> NOON
                currentTime.isBefore(LocalTime.of(18, 0)) -> AFTERNOON
                else -> EVENING
            }
        }
    }


    private val quotes = context.resources.getStringArray(R.array.quotes).toList()

    private val _schedules = MutableLiveData<ScheduleResult>(ScheduleResult.EmptySchedules)
    val schedules: LiveData<ScheduleResult>
        get() = _schedules

    private val _quote = MutableLiveData(quotes.random())
    val quote : LiveData<String>
        get() = _quote

    private val _timeOfDay = MutableLiveData(TimeOfDay.from(LocalTime.now()))
    val timeOfDay : LiveData<TimeOfDay>
        get() = _timeOfDay

    fun changeQuote(){
        _quote.postValue(quotes.random())
    }

    fun listSchedules(scheduleType: ScheduleType){
        viewModelScope.launch(Dispatchers.IO) {
            when(scheduleType){
                ScheduleType.TODAY -> {
                    _schedules.postValue(ScheduleResult.Schedules(scheduleRepository.getCurrentSchedule()))
                }
                ScheduleType.TOMORROW -> {
                    _schedules.postValue(ScheduleResult.Schedules(scheduleRepository.getTomorrowSchedule()))
                }
                ScheduleType.WEEK -> {
                    _schedules.postValue(ScheduleResult.WeekSchedules(scheduleRepository.getWeekSchedule()))
                }
                ScheduleType.ALL -> {
                    _schedules.postValue(ScheduleResult.EmptySchedules)
                }
            }
        }
    }

}