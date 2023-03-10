package com.shiftkey.codingchallenge.domain.model

import com.shiftkey.codingchallenge.core.converter.toSystemZonedLocalDateTime
import com.shiftkey.codingchallenge.data.response.shifts.*
import com.shiftkey.codingchallenge.data.response.shifts.LocalizedSpecialtyResponse
import java.time.LocalDateTime
import java.time.OffsetDateTime

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
    val timezone: Timezone,
    val withinDistance: Int
)

fun ShiftResponse.toDomain() = Shift(
    covid = covid,
    startTime = startTime.toSystemZonedLocalDateTime(),
    endTime = endTime.toSystemZonedLocalDateTime(),
    facilityType = facilityType.toDomain(),
    localizedSpecialty = localizedSpecialty.toDomain(),
    skill = skill.toDomain(),
    timezone = Timezone.from(timezone),
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

enum class Timezone(
    private val apiString: String
    ) {
    CENTRAL("Central"),
    UNKNOWN("");

    companion object {
        fun from(apiString: String) = values().firstOrNull { it.apiString == apiString } ?: UNKNOWN
    }
}

data class Skill(
    val color: String,
    val id: Int,
    val name: String
)

fun SkillResponse.toDomain() = Skill(
    color = color,
    id = id,
    name = name
)

data class Facility(
    val color: String,
    val id: Int,
    val name: String
)

fun FacilityTypeResponse.toDomain() = Facility(
    color = color,
    id = id,
    name = name
)

data class LocalizedSpecialty(
    val abbreviation: String,
    val id: Int,
    val name: String,
    val specialty: Specialty,
    val specialtyId: Int,
    val stateId: Int
)

fun LocalizedSpecialtyResponse.toDomain() = LocalizedSpecialty(
    abbreviation = abbreviation,
    id = id,
    name = name,
    specialty = specialty.toDomain(),
    specialtyId = specialtyId,
    stateId = stateId
)

data class Specialty(
    val abbreviation: String,
    val color: String,
    val id: Int,
    val name: String
)

fun SpecialtyResponse.toDomain() = Specialty(
    abbreviation = abbreviation,
    color = color,
    id = id,
    name = name
)
