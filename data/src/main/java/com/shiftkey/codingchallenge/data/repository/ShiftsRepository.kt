package com.shiftkey.codingchallenge.data.repository

import com.shiftkey.codingchallenge.data.ResponseMapper
import com.shiftkey.codingchallenge.data.api.ShiftsApi
import com.shiftkey.codingchallenge.data.exceptions.ApiException
import com.shiftkey.codingchallenge.data.response.shifts.BaseShiftsResponse
import java.time.LocalDateTime
import javax.inject.Inject

class ShiftsRepository @Inject constructor(
    private val shiftsApi: ShiftsApi,
    private val responseMapper: ResponseMapper
) {

    suspend fun getShifts(
        address: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        type: String,
        radius: Int
    ): BaseShiftsResponse {
        val response = responseMapper.map(
            shiftsApi.getAvailableShifts(
                address, startDateTime, endDateTime, type, radius
            )
        )
        if (response.data.isEmpty()) throw ApiException.EmptyResponseException()
        return response
    }
}
