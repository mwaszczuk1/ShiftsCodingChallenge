package com.shiftkey.codingchallenge.domain.di

import com.shiftkey.codingchallenge.core.DispatchersProvider
import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideNetowrkErrorHandler(): NetworkErrorHandler = NetworkErrorHandler()

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProvider()
}
