package com.shiftkey.codingchallenge.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersProvider(
    val io: CoroutineDispatcher = Dispatchers.IO
)
