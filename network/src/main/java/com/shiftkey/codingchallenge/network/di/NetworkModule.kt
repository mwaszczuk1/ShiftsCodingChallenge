package com.shiftkey.codingchallenge.network.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shiftkey.codingchallenge.network.BuildConfig
import com.shiftkey.codingchallenge.network.converter.LocalDateSerializer
import com.shiftkey.codingchallenge.network.converter.OffsetDateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.OffsetDateTime

@InstallIn(SingletonComponent::class)
@Module
class WebSocketsModule {

    @Provides
    @Singleton
    fun provideOkHttp(chucker: ChuckerInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(chucker)
        .build()

    @Singleton
    @Provides
    fun provideChuckerInterceptor(app: Application): ChuckerInterceptor = ChuckerInterceptor(app)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}