package com.shiftkey.codingchallenge.core.converter

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

fun OffsetDateTime.toSystemZonedLocalDateTime(): LocalDateTime =
    this.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
