package com.shiftkey.codingchallenge.shifts.list

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shiftkey.codingchallenge.design.DesignDrawables
import com.shiftkey.codingchallenge.design.components.Chip
import com.shiftkey.codingchallenge.design.theme.LightBlue
import com.shiftkey.codingchallenge.design.theme.Orange
import com.shiftkey.codingchallenge.design.theme.Yellow
import com.shiftkey.codingchallenge.domain.model.*

@Composable
fun ShiftItem(
    shift: Shift,
    onClick: (Shift) -> Unit
) {
    Card(elevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(shift) }
                .padding(16.dp)
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
                    text = shift.startTime,
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Chip(
                        text = shift.withinDistance,
                        icon = painterResource(DesignDrawables.ic_location),
                        color = MaterialTheme.colors.onBackground
                    )

                    Chip(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = shift.facilityType.name,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }

            Column {
                if (shift.premiumRate) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.End),
                        painter = painterResource(DesignDrawables.ic_star),
                        tint = Yellow,
                        contentDescription = ""
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .size(24.dp),
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
        shift = Shift(
            shiftId = 1,
            covid = false,
            startTime = "12:00",
            endTime = "13:00",
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
            timezone = Timezone.CENTRAL,
            withinDistance = "10 MI"
        ),
        onClick = {}
    )
}
