package com.shiftkey.codingchallenge.data.response.shifts

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class ShiftResponse(
    @SerializedName("covid")
    val covid: Boolean,
    @SerializedName("start_time")
    val startTime: OffsetDateTime,
    @SerializedName("end_time")
    val endTime: OffsetDateTime,
    @SerializedName("facility_type")
    val facilityType: FacilityTypeResponse,
    @SerializedName("localized_specialty")
    val localizedSpecialty: LocalizedSpecialtyResponse,
    @SerializedName("premium_rate")
    val premiumRate: Boolean,
    @SerializedName("shift_id")
    val shiftId: Int,
    @SerializedName("shift_kind")
    val shiftKind: String,
    @SerializedName("skill")
    val skill: SkillResponse,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("within_distance")
    val withinDistance: Int
)
