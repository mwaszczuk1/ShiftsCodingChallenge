package com.shiftkey.codingchallenge.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersProvider(
    val ui: CoroutineDispatcher = Dispatchers.Main,
    val default: CoroutineDispatcher = Dispatchers.Default,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)
