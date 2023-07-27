package com.tianjun.tls_tkb.di

import android.content.Context
import com.tianjun.tls_tkb.data.local.repository.MainPreferenceRepositoryImpl
import com.tianjun.tls_tkb.domain.repository.MainPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context : Context) =
        context.getSharedPreferences("MainSharedPreferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideMainPreferenceRepository(
        mainPreferenceRepositoryImpl: MainPreferenceRepositoryImpl
    ) : MainPreferenceRepository = mainPreferenceRepositoryImpl
}