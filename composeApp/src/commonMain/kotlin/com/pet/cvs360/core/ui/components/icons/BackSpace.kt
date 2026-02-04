package com.pet.cvs360.core.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val BackSpace: ImageVector
    get() = ImageVector.Builder(
        name = "BackSpace",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Red)) {
            moveTo(22f, 3f)
            horizontalLineTo(7f)
            curveToRelative(-0.69f, 0f, -1.23f, 0.35f, -1.59f, 0.88f)
            lineTo(0f, 12f)
            lineToRelative(5.41f, 8.11f)
            curveToRelative(0.36f, 0.53f, 0.9f, 0.89f, 1.59f, 0.89f)
            horizontalLineToRelative(15f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineTo(5f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(19f, 15.59f)
            lineTo(17.59f, 17f)
            lineTo(14f, 13.41f)
            lineTo(10.41f, 17f)
            lineTo(9f, 15.59f)
            lineTo(12.59f, 12f)
            lineTo(9f, 8.41f)
            lineTo(10.41f, 7f)
            lineTo(14f, 10.59f)
            lineTo(17.59f, 7f)
            lineTo(19f, 8.41f)
            lineTo(15.41f, 12f)
            lineTo(19f, 15.59f)
            close()
        }
    }.build()
