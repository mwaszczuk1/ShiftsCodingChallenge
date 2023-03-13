package com.shiftkey.codingchallenge.shifts.list.model

import androidx.compose.runtime.Immutable
import com.shiftkey.codingchallenge.domain.model.shift.*
import java.time.LocalDate
import java.time.LocalDateTime
import com.shiftkey.codingchallenge.domain.model.shift.Shift as DomainShift

@Immutable
sealed class ShiftListItem(
    open val contentType: String
) {

    @Immutable
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
    ) : ShiftListItem(contentType = "Shift") {
        companion object {
            fun DomainShift.toUi() = Shift(
                shiftId, covid, startTime, endTime, facilityType, localizedSpecialty, premiumRate, shiftKind, skill, withinDistance
            )
        }

        fun toDomain() = DomainShift(
            shiftId, covid, startTime, endTime, facilityType, localizedSpecialty, premiumRate, shiftKind, skill, withinDistance
        )
    }

    @Immutable
    data class Header(
        val date: LocalDate
    ) : ShiftListItem(contentType = "Header")
}
