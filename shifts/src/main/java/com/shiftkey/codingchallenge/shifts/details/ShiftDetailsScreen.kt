package com.shiftkey.codingchallenge.shifts.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.shiftkey.codingchallenge.core.formatter.durationTo
import com.shiftkey.codingchallenge.core.formatter.getDateLabel
import com.shiftkey.codingchallenge.core.formatter.toAMPMTimeRange
import com.shiftkey.codingchallenge.design.DesignDrawables
import com.shiftkey.codingchallenge.design.components.Chip
import com.shiftkey.codingchallenge.design.components.Divider
import com.shiftkey.codingchallenge.design.components.ItemRow
import com.shiftkey.codingchallenge.design.components.SheetHandle
import com.shiftkey.codingchallenge.design.components.topBar.LocalTopBar
import com.shiftkey.codingchallenge.design.theme.*
import com.shiftkey.codingchallenge.domain.model.shift.*
import com.shiftkey.codingchallenge.domain.useCase.ShiftDetails
import com.shiftkey.codingchallenge.shifts.R
import com.shiftkey.codingchallenge.shifts.details.map.DEFAULT_MAP_SIZE
import com.shiftkey.codingchallenge.shifts.details.map.ShiftDetailsMap
import java.time.LocalDateTime

@Composable
fun ShiftDetailsScreen(
    viewModel: ShiftDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    with(LocalTopBar.current) {
        toggleNavigationIcon(true)
        setTitle(stringResource(R.string.shift_details_screen_title))
    }

    state?.let {
        ShiftDetailsLayout(it)
    }
}

@Composable
fun ShiftDetailsLayout(
    shift: ShiftDetails
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(-SizeXL)
        ) {
            item {
                ShiftDetailsMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DEFAULT_MAP_SIZE)
                        .graphicsLayer {
                            translationY =
                                -((lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset?.toFloat()
                                    ?: 0f) / 1.3f)
                        },
                    location = LatLng(shift.lat, shift.lng),
                    withinDistance = shift.shift.withinDistance.toDouble()
                )
            }
            item {
                InfoCardSection(shift)
            }
        }
    }
}

@Composable
fun LazyItemScope.InfoCardSection(
    shift: ShiftDetails
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillParentMaxHeight(),
        shape = RoundedCornerShape(SizeXL, SizeXL, 0.dp, 0.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = SizeXXXXS
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SheetHandle(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = SizeS)
            )
            Text(
                modifier = Modifier
                    .padding(start = SizeM, end = SizeM, top = SizeM),
                text = shift.shift.facilityType.name,
                style = MaterialTheme.typography.h3
            )

            ItemRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SizeS, end = SizeS)
                    .padding(vertical = SizeXS),
                text = shift.location,
                bottomText = stringResource(
                    R.string.shift_details_screen_approx_location,
                    shift.shift.withinDistance
                ),
                icon = painterResource(DesignDrawables.ic_location),
                iconTint = MaterialTheme.colors.onBackground
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeS)
                    .padding(vertical = SizeXXS),
                color = MaterialTheme.colors.onBackground
            )

            ItemRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SizeS, end = SizeS)
                    .padding(vertical = SizeXS),
                text = shift.shift.startTime.getDateLabel(LocalContext.current),
                bottomText = shift.shift.startTime.toAMPMTimeRange(shift.shift.endTime),
                icon = painterResource(DesignDrawables.ic_calendar),
                iconTint = MaterialTheme.colors.onBackground
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeS)
                    .padding(vertical = SizeXXS),
                color = MaterialTheme.colors.onBackground
            )

            ItemRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SizeS, end = SizeS)
                    .padding(vertical = SizeXS),
                text = stringResource(R.string.shift_details_screen_time_until_shift_start),
                bottomText = LocalDateTime.now().durationTo(
                    shift.shift.startTime,
                    LocalContext.current
                ),
                icon = painterResource(DesignDrawables.ic_clock),
                iconTint = MaterialTheme.colors.onBackground
            )

            if (shift.shift.premiumRate) {
                ItemRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = SizeS, end = SizeS)
                        .padding(top = SizeS, bottom = SizeXXS),
                    text = stringResource(R.string.shift_details_screen_premium_shift),
                    icon = painterResource(DesignDrawables.ic_star),
                    iconDescription = "premium_shift",
                    iconTint = Yellow
                )
            }

            ItemRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SizeS, end = SizeS)
                    .padding(top = SizeS),
                text = stringResource(
                    when (shift.shift.shiftKind) {
                        ShiftKind.DAY_SHIFT -> R.string.shift_details_screen_day_shift
                        ShiftKind.EVENING_SHIFT -> R.string.shift_details_screen_evening_shift
                        ShiftKind.NIGHT_SHIFT -> R.string.shift_details_screen_night_shift
                        else -> R.string.shift_details_screen_unknown_shift
                    }
                ),
                icon = painterResource(
                    when (shift.shift.shiftKind) {
                        ShiftKind.EVENING_SHIFT -> DesignDrawables.ic_evening
                        ShiftKind.NIGHT_SHIFT -> DesignDrawables.ic_night
                        else -> DesignDrawables.ic_sun
                    }
                ),
                iconTint = when (shift.shift.shiftKind) {
                    ShiftKind.EVENING_SHIFT -> Orange
                    ShiftKind.NIGHT_SHIFT -> LightBlue
                    else -> Yellow
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeS)
                    .padding(top = SizeL)
            ) {
                Chip(
                    text = shift.shift.skill.name,
                    color = Color(shift.shift.skill.color.toColorInt())
                )
                Chip(
                    modifier = Modifier
                        .padding(start = SizeXXS),
                    text = shift.shift.localizedSpecialty.specialty.name,
                    color = Color(shift.shift.localizedSpecialty.specialty.color.toColorInt())
                )
            }
        }
    }
}

const val SHIFT_DETAILS_SCREEN_ROUTE = "shift_details"

@Preview
@Composable
fun ShiftsDetailsScreenPreview() {
    ShiftDetailsLayout(
        shift = ShiftDetails(
            lat = 20.0,
            lng = 20.0,
            shift = Shift(
                shiftId = 1,
                covid = false,
                startTime = LocalDateTime.now(),
                endTime =  LocalDateTime.now(),
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
            location = "Dallas, TX"
        )
    )
}
