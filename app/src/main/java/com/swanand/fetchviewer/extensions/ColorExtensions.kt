package com.swanand.fetchviewer.extensions


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun Color.lighter(factor: Float = 0.5f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), factor))

fun Color.darker(factor: Float = 0.1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.Gray.toArgb(), factor))