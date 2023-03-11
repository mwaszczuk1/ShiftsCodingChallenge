package com.shiftkey.codingchallenge.domain.model.shift

enum class ShiftKind(private val apiString: String) {
    DAY_SHIFT("Day Shift"),
    EVENING_SHIFT("Evening Shift"),
    NIGHT_SHIFT("Night Shift"),
    UNSUPPORTED("");

    companion object {
        fun from(apiString: String) = values().firstOrNull { it.apiString == apiString } ?: UNSUPPORTED
    }
}
