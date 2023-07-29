package com.tianjun.tls_tkb.domain.usecase

import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.domain.repository.MainPreferenceRepository
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import com.tianjun.tls_tkb.util.view.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAllDataUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val subjectRepository: SubjectInfoRepository,
    private val timetableRepository: TimetableRepository,
    private val mainPreferenceRepository: MainPreferenceRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading("Đang xóa..."))
        try {
            mainPreferenceRepository.setHostName(BuildConfig.HOST_NAME)
            mainPreferenceRepository.setHostPort(BuildConfig.HOST_PORT)
            roomRepository.deleteAllRooms()
            subjectRepository.deleteAllSubjects()
            timetableRepository.deleteAllTimetables()
            emit(Result.Success("Xóa thành công!"))
        }
        catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
}