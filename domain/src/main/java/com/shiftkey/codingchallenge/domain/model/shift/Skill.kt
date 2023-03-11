package com.shiftkey.codingchallenge.domain.model.shift

import com.shiftkey.codingchallenge.data.response.shifts.SkillResponse

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
