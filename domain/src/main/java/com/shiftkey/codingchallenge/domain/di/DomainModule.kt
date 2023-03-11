package com.shiftkey.codingchallenge.domain.di

import com.shiftkey.codingchallenge.core.DispatchersProvider
import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DataModule {

    @Provides
    fun provideNetowrkErrorHandler(): NetworkErrorHandler = NetworkErrorHandler()

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider = DispatchersProvider()
}
