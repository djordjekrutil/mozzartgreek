package com.djordjekrutil.mozzartgreek.feature.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.random.Random

fun randomColor(): Color {
    // Generate a random ARGB color
    return Color(
        alpha = 255,
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )
}

fun generateRandomNumbers(range: IntRange, count: Int): List<Int> {
    return List(count) { Random.nextInt(range.first, range.last + 1) }.distinct().take(count)
}

fun dpToSp(dp: Dp, context: Context): TextUnit {
    val resources = context.resources
    val metrics = resources.displayMetrics
    // Convert dp to pixels
    val pixels = dp.value * (metrics.densityDpi / 160f)
    // Convert pixels to sp
    val sp = pixels / metrics.scaledDensity
    return sp.sp
}