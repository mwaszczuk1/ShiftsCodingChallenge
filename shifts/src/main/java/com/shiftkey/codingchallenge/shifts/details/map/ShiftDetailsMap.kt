package com.shiftkey.codingchallenge.shifts.details.map

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.shiftkey.codingchallenge.shifts.R
import kotlin.math.ln

private const val MILE_IN_METERS = 1609.34
private const val MILE_IN_KILOMETERS = 1.60934
val DEFAULT_MAP_SIZE = 324.dp

@Composable
internal fun ShiftDetailsMap(
    modifier: Modifier = Modifier,
    location: LatLng,
    withinDistance: Double
) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(location, calculateZoomForRadius(withinDistance))
    }

    val mapUiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = false
        )
    }
    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                context,
                R.raw.map_style_json
            )
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
        properties = mapProperties
    ) {
        Marker(
            state = MarkerState(position = location)
        )
        Circle(
            center = location,
            radius = withinDistance * MILE_IN_METERS,
            fillColor = MaterialTheme.colors.primaryVariant,
            strokeColor = MaterialTheme.colors.primary
        )
    }
}

private fun calculateZoomForRadius(radiusMiles: Double): Float {
    val dist = radiusMiles * (MILE_IN_KILOMETERS + 0.4)
    val scale = (dist + dist / 2) / 500
    return (6 - ln(scale) / ln(2.0)).toFloat()
}
