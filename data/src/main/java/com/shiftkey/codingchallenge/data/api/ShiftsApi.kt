package com.shiftkey.codingchallenge.data.api

import retrofit2.http.GET

interface ShiftsApi {

    @GET("available_shifts")
    suspend fun getAvailableShifts(): Unit
}
