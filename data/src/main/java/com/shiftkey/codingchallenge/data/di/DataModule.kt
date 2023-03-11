package com.shiftkey.codingchallenge.data.di

import com.shiftkey.codingchallenge.data.ResponseMapper
import com.shiftkey.codingchallenge.data.api.ShiftsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideShiftsApi(retrofit: Retrofit): ShiftsApi =
        retrofit.create(ShiftsApi::class.java)

    @Provides
    fun provideResponseMapper(): ResponseMapper = ResponseMapper()
}
