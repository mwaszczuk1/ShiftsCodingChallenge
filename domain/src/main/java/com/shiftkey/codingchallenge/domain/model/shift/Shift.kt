package com.shiftkey.codingchallenge.domain.model.shift

import com.shiftkey.codingchallenge.core.converter.toSystemZonedLocalDateTime
import com.shiftkey.codingchallenge.core.formatter.prependUSBaseTimeZone
import com.shiftkey.codingchallenge.data.response.shifts.*
import java.time.LocalDateTime

data class Shift(
    val shiftId: Int,
    val covid: Boolean,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val facilityType: Facility,
    val localizedSpecialty: LocalizedSpecialty,
    val premiumRate: Boolean,
    val shiftKind: ShiftKind,
    val skill: Skill,
    val withinDistance: Int
)

fun ShiftResponse.toDomain() = Shift(
    covid = covid,
    startTime = startTime.toSystemZonedLocalDateTime(timezone.prependUSBaseTimeZone()),
    endTime = endTime.toSystemZonedLocalDateTime(timezone.prependUSBaseTimeZone()),
    facilityType = facilityType.toDomain(),
    localizedSpecialty = localizedSpecialty.toDomain(),
    skill = skill.toDomain(),
    withinDistance = withinDistance,
    shiftId = shiftId,
    premiumRate = premiumRate,
    shiftKind = ShiftKind.from(shiftKind)
)
