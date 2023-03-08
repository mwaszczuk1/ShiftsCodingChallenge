package com.shiftkey.codingchallenge.data.api

import com.shiftkey.codingchallenge.data.response.shifts.BaseShiftsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

interface ShiftsApi {

    @GET("available_shifts")
    suspend fun getAvailableShifts(
        @Query("address") address: String,
        @Query("start") startDateTime: LocalDateTime,
        @Query("end") endDateTime: LocalDateTime,
        @Query("type") type: String,
        @Query("radius") radius: Int
    ): Response<BaseShiftsResponse>
}
