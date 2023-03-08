package com.shiftkey.codingchallenge.domain.util

import com.shiftkey.codingchallenge.data.exceptions.ApiException
import com.shiftkey.codingchallenge.domain.base.ViewState
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorHandler {
    fun handle(throwable: Throwable): ViewState.Error {
        Timber.tag(NETWORK_ERROR_TAG).e(throwable)
        return when (throwable) {
            is UnknownHostException -> ViewState.Error(
                errorMessage = "Please check your internet connection"
            )
            is SocketTimeoutException -> ViewState.Error(
                errorMessage = "Looks like the server is taking to long to respond"
            )
            is ApiException -> ViewState.Error(
                errorMessage = throwable.message
            )
            else -> ViewState.Error(
                errorMessage = "Oops, something went wrong. Please try again later."
            )
        }
    }

    companion object {
        private const val NETWORK_ERROR_TAG = "NETWORK_ERROR"
    }
}
