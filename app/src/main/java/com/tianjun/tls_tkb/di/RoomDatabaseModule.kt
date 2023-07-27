package com.tianjun.tls_tkb.di

import android.content.Context
import androidx.room.Room
import com.tianjun.tls_tkb.module.ApplicationRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun provideApplicationRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ApplicationRoomDatabase::class.java, "AppDatabase").build()

    @Provides
    @Singleton
    fun provideRoomDao(applicationRoomDatabase: ApplicationRoomDatabase) =
        applicationRoomDatabase.roomDao()

    @Provides
    @Singleton
    fun provideSubjectInfoDao(applicationRoomDatabase: ApplicationRoomDatabase) =
        applicationRoomDatabase.subjectInfoDao()

    @Provides
    @Singleton
    fun provideTimetableDao(applicationRoomDatabase: ApplicationRoomDatabase) =
        applicationRoomDatabase.timetableDao()

    @Provides
    @Singleton
    fun provideScheduleDao(applicationRoomDatabase: ApplicationRoomDatabase) =
        applicationRoomDatabase.scheduleDao()

}