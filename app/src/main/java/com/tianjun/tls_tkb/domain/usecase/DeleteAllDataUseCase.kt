package com.tianjun.tls_tkb.domain.usecase

import com.tianjun.tls_tkb.domain.repository.RoomRepository
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import javax.inject.Inject

class DeleteAllDataUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val subjectRepository: SubjectInfoRepository,
    private val timetableRepository: TimetableRepository
) {
    suspend fun execute(){
        roomRepository.deleteAllRooms()
        subjectRepository.deleteAllSubjects()
        timetableRepository.deleteAllTimetables()
    }
}