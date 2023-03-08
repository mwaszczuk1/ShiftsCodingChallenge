package com.shiftkey.codingchallenge.domain.base

sealed class ViewState<out T> {

    object Loading : ViewState<Nothing>()
    class Error(val errorMessage: String) : ViewState<Nothing>()
    class Success<T>(val data: T) : ViewState<T>()
}
