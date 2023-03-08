package com.shiftkey.codingchallenge.network.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shiftkey.codingchallenge.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

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
    fun provideChuckerInterceptor(app: Application) = ChuckerInterceptor(app)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
