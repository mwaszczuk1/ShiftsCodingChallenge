package com.shiftkey.codingchallenge.domain.model.shift

import com.shiftkey.codingchallenge.data.response.shifts.FacilityTypeResponse

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
