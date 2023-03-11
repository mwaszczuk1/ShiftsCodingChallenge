package com.shiftkey.codingchallenge.domain.useCase.shifts

import com.shiftkey.codingchallenge.data.response.shifts.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

private val shiftResponse = ShiftResponse(
    covid = false,
    startTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC),
    endTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(3),
    facilityType = FacilityTypeResponse("color", 1, "facilityName"),
    localizedSpecialty = LocalizedSpecialtyResponse(
        abbreviation = "abbreviation",
        id = 1,
        name = "localizedSpecialty",
        specialty = SpecialtyResponse(
            abbreviation = "abv",
            color = "color",
            id = 1,
            name = "specialtyName"
        ),
        specialtyId = 1,
        stateId = 2
    ),
    premiumRate = false,
    shiftId = 1,
    shiftKind = "Day Shift",
    skill = SkillResponse("color", 1, "skillName"),
    timezone = "Central",
    withinDistance = 10
)

val getShiftsListUseCaseInput = BaseShiftsResponse(
    data = listOf(
        ShiftsListResponse(
            date = LocalDate.of(2023, 3, 11),
            shifts = listOf(
                shiftResponse,
                shiftResponse.copy(
                    startTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(5),
                    endTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(7),
                    shiftKind = "Evening Shift",
                    shiftId = 2
                ),
                shiftResponse.copy(
                    startTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(11),
                    endTime = OffsetDateTime.of(2023, 3, 11, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(15),
                    shiftKind = "Night Shift",
                    shiftId = 3
                )
            )
        ),
        ShiftsListResponse(
            date = LocalDate.of(2023, 3, 12),
            shifts = listOf(
                shiftResponse.copy(
                    startTime = OffsetDateTime.of(2023, 3, 12, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(5),
                    endTime = OffsetDateTime.of(2023, 3, 12, 0, 0, 0, 0, ZoneOffset.UTC).plusHours(7),
                    shiftKind = "Evening Shift",
                    shiftId = 4
                )
            )
        )
    ),
    meta = ShiftsMetaResponse(69.0, 69.0)
)
