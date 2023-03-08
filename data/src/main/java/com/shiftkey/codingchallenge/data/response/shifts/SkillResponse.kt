package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName

data class SkillResponse(
    @SerializedName("color")
    val color: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
