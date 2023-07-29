package com.tianjun.tls_tkb.di

import com.tianjun.tls_tkb.data.local.repository.MainDataStoreRepositoryImpl
import com.tianjun.tls_tkb.data.local.repository.RoomRepositoryImpl
import com.tianjun.tls_tkb.data.local.repository.ScheduleRepositoryImpl
import com.tianjun.tls_tkb.data.local.repository.SubjectInfoRepositoryImpl
import com.tianjun.tls_tkb.data.local.repository.TimetableRepositoryImpl
import com.tianjun.tls_tkb.data.remote.repository.ServerInfoRepositoryImpl
import com.tianjun.tls_tkb.domain.repository.MainDataStoreRepository
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import com.tianjun.tls_tkb.domain.repository.ScheduleRepository
import com.tianjun.tls_tkb.domain.repository.ServerInfoRepository
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository =
        roomRepositoryImpl

    @Provides
    @Singleton
    fun provideMainDataStoreRepository(mainDataStoreRepositoryImpl: MainDataStoreRepositoryImpl): MainDataStoreRepository =
        mainDataStoreRepositoryImpl

    @Provides
    @Singleton
    fun provideScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl) : ScheduleRepository =
        scheduleRepositoryImpl

    @Provides
    @Singleton
    fun provideSubjectInfoRepository(subjectInfoRepositoryImpl: SubjectInfoRepositoryImpl): SubjectInfoRepository =
        subjectInfoRepositoryImpl

    @Provides
    @Singleton
    fun provideTimetableRepository(timetableRepositoryImpl: TimetableRepositoryImpl): TimetableRepository =
        timetableRepositoryImpl

}