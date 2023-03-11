package com.shiftkey.codingchallenge.shifts.viewModel.ui

import com.shiftkey.codingchallenge.domain.model.shift.*
import com.shiftkey.codingchallenge.domain.useCase.ShiftDetails
import java.time.LocalDateTime

val shiftDomain = Shift(
    shiftId = 1,
    covid = false,
    startTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0),
    endTime = LocalDateTime.of(2023, 3, 11, 0, 0, 0, 0).plusHours(3),
    facilityType = Facility("#007AFF", 1, "facilityName"),
    localizedSpecialty = LocalizedSpecialty(
        abbreviation = "abbreviation",
        id = 1,
        name = "localizedSpecialty",
        specialty = Specialty(
            abbreviation = "abv",
            color = "#007AFF",
            id = 1,
            name = "specialtyName"
        ),
        specialtyId = 1,
        stateId = 2
    ),
    premiumRate = false,
    shiftKind = ShiftKind.DAY_SHIFT,
    skill = Skill("#007AFF", 1, "skillName"),
    withinDistance = 10
)

val shiftDetails = ShiftDetails(
    shift = shiftDomain,
    location = "Dallas, TX",
    lat = 1.0,
    lng = 1.0
)
