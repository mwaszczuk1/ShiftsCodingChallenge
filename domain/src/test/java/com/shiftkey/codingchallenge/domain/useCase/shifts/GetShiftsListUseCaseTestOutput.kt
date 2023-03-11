package com.shiftkey.codingchallenge.domain.useCase.shifts

import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.shift.*
import java.time.LocalDate
import java.time.LocalDateTime

private val shiftDomain = Shift(
    shiftId = 1,
    covid = false,
    startTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0),
    endTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(3),
    facilityType = Facility("color", 1, "facilityName"),
    localizedSpecialty = LocalizedSpecialty(
        abbreviation = "abbreviation",
        id = 1,
        name = "localizedSpecialty",
        specialty = Specialty(
            abbreviation = "abv",
            color = "color",
            id = 1,
            name = "specialtyName"
        ),
        specialtyId = 1,
        stateId = 2
    ),
    premiumRate = false,
    shiftKind = ShiftKind.DAY_SHIFT,
    skill = Skill("color", 1, "skillName"),
    withinDistance = 10
)

val expectedGetShiftsListUseCaseOutput = ViewState.Success(
    ShiftsList(
        lat = 69.0,
        lng = 69.0,
        shifts = mapOf(
            LocalDate.of(2023, 3, 11) to listOf(
                shiftDomain,
                shiftDomain.copy(
                    startTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(5),
                    endTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(7),
                    shiftKind = ShiftKind.EVENING_SHIFT,
                    shiftId = 2
                ),
                shiftDomain.copy(
                    startTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(11),
                    endTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(15),
                    shiftKind = ShiftKind.NIGHT_SHIFT,
                    shiftId = 3
                )
            ),
            LocalDate.of(2023, 3, 12) to listOf(
                shiftDomain.copy(
                    startTime = LocalDateTime.of(2023, 3, 12, 0, 0, 0, 0).plusHours(5),
                    endTime = LocalDateTime.of(2023, 3, 12, 0, 0, 0, 0).plusHours(7),
                    shiftKind = ShiftKind.EVENING_SHIFT,
                    shiftId = 4
                )
            )
        )
    )
)
