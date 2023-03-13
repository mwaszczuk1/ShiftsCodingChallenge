package com.shiftkey.codingchallenge.shifts.list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.shiftkey.codingchallenge.core.formatter.toAMPMTimeRange
import com.shiftkey.codingchallenge.design.DesignDrawables
import com.shiftkey.codingchallenge.design.components.Chip
import com.shiftkey.codingchallenge.design.theme.*
import com.shiftkey.codingchallenge.domain.model.shift.*
import com.shiftkey.codingchallenge.shifts.list.model.ShiftListItem
import java.time.LocalDateTime

@Composable
fun ShiftItem(
    shift: ShiftListItem.Shift,
    onClick: (ShiftListItem.Shift) -> Unit
) {
    Card(elevation = SizeXXXS) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(shift) }
                .padding(SizeS)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = shift.facilityType.name,
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = shift.startTime.toAMPMTimeRange(shift.endTime),
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground)
                )

                Row(
                    modifier = Modifier
                        .padding(top = SizeXXS)
                ) {
                    Chip(
                        text = "${shift.withinDistance} MI",
                        icon = painterResource(DesignDrawables.ic_location),
                        color = MaterialTheme.colors.onBackground
                    )

                    Chip(
                        modifier = Modifier
                            .padding(start = SizeXXS),
                        text = shift.skill.name,
                        color = Color(shift.skill.color.toColorInt())
                    )
                }
            }

            Column {
                if (shift.premiumRate) {
                    Icon(
                        modifier = Modifier
                            .size(SizeM)
                            .align(Alignment.End),
                        painter = painterResource(DesignDrawables.ic_star),
                        tint = Yellow,
                        contentDescription = ""
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(top = SizeL)
                        .size(SizeM),
                    painter = painterResource(
                        when (shift.shiftKind) {
                            ShiftKind.NIGHT_SHIFT -> DesignDrawables.ic_night
                            ShiftKind.EVENING_SHIFT -> DesignDrawables.ic_evening
                            ShiftKind.DAY_SHIFT -> DesignDrawables.ic_sun
                            else -> DesignDrawables.ic_sun
                        }
                    ),
                    colorFilter = ColorFilter.tint(
                        when (shift.shiftKind) {
                            ShiftKind.NIGHT_SHIFT -> LightBlue
                            ShiftKind.EVENING_SHIFT -> Orange
                            ShiftKind.DAY_SHIFT -> Yellow
                            else -> Yellow
                        }
                    ),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun ShiftItemPreview() {
    ShiftItem(
        shift = ShiftListItem.Shift(
            shiftId = 1,
            covid = false,
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            facilityType = Facility("", 1, "Facility"),
            localizedSpecialty = LocalizedSpecialty(
                abbreviation = "abc",
                id = 1,
                name = "Specialty",
                specialty = Specialty("abc", "", 1, "Specialty"),
                specialtyId = 1,
                stateId = 1
            ),
            premiumRate = true,
            shiftKind = ShiftKind.DAY_SHIFT,
            skill = Skill("", 1, "Skill"),
            withinDistance = 10
        ),
        onClick = {}
    )
}
