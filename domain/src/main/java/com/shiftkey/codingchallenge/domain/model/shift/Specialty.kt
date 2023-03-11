package com.shiftkey.codingchallenge.domain.model.shift

import com.shiftkey.codingchallenge.data.response.shifts.LocalizedSpecialtyResponse
import com.shiftkey.codingchallenge.data.response.shifts.SpecialtyResponse

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
