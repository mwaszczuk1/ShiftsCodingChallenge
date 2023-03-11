package com.shiftkey.codingchallenge.data

import com.shiftkey.codingchallenge.data.exceptions.ApiException
import retrofit2.Response

class ResponseMapper {

    fun <T> map(response: Response<T>): T {
        val responseBody = response.body()
        val errorBody = response.errorBody()
        if (errorBody != null) {
            // I would probably send a non-fatal event to crashlytics here
        }
        return when {
            response.isSuccessful && responseBody != null -> responseBody
            response.code() == 204 -> throw ApiException.EmptyResponseException()
            response.code() in (500..599) -> {
                throw ApiException.ServiceUnavailableException()
            }
            else -> {
                throw ApiException.UnexpectedException()
            }
        }
    }
}
