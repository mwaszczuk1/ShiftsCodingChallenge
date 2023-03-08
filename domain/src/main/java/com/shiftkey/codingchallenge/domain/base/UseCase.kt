package com.shiftkey.codingchallenge.domain.base

import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler

abstract class UseCase<T>(
    private val networkErrorHandler: NetworkErrorHandler
) {
    protected suspend fun safeApiCall(block: suspend () -> T) : ViewState<T> {
        return try {
            ViewState.Success(block())
        } catch (e: Throwable) {
            networkErrorHandler.handle(e)
        }
    }
}
