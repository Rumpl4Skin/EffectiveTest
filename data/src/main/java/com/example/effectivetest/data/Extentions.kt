package com.example.effectivetest.data

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundToDecimalPlaces(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).roundToInt() / factor
}