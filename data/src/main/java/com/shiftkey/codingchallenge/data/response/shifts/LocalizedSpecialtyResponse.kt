package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName

data class LocalizedSpecialtyResponse(
    @SerializedName("abbreviation")
    val abbreviation: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("specialty")
    val specialty: SpecialtyResponse,
    @SerializedName("specialty_id")
    val specialtyId: Int,
    @SerializedName("state_id")
    val stateId: Int
)
