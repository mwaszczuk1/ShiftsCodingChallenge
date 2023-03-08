package com.shiftkey.codingchallenge.domain.model

import com.shiftkey.codingchallenge.data.response.shifts.FacilityTypeResponse
import com.shiftkey.codingchallenge.data.response.shifts.LocalizedSpecialtyResponse
import com.shiftkey.codingchallenge.data.response.shifts.ShiftResponse
import com.shiftkey.codingchallenge.data.response.shifts.SkillResponse

data class Shift(
    val covid: Boolean,
    val endTime: String,
    val facilityType: FacilityTypeResponse,
    val localizedSpecialty: LocalizedSpecialtyResponse,
    val normalizedEndDateTime: String,
    val normalizedStartDateTime: String,
    val premiumRate: Boolean,
    val shiftId: Int,
    val shiftKind: ShiftKind,
    val skill: SkillResponse,
    val startTime: String,
    val timezone: String,
    val withinDistance: Int
)

fun ShiftResponse.toDomain() = Shift(
    covid = covid,
    startTime = startTime.toString(),
    endTime = endTime.toString(),
    normalizedStartDateTime = normalizedStartDateTime,
    normalizedEndDateTime = normalizedEndDateTime,
    facilityType = facilityType,
    localizedSpecialty = localizedSpecialty,
    skill = skill,
    timezone = timezone,
    withinDistance = withinDistance,
    shiftId = shiftId,
    premiumRate = premiumRate,
    shiftKind = ShiftKind.from(shiftKind)
)

enum class ShiftKind(private val apiString: String) {
    DAY_SHIFT("Day Shift"),
    EVENING_SHIFT("Evening Shift"),
    NIGHT_SHIFT("Night Shift"),
    UNSUPPORTED("");

    companion object {
        fun from(apiString: String) = values().firstOrNull { it.apiString == apiString } ?: UNSUPPORTED
    }
}
