package com.pet.cvs360.core.ui.components.pin_keypad

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun PinDots(
    pin: String,
    maxLength: Int = 4,
    isError: Boolean = false,
) {
    val shakeOffset = remember { Animatable(0f) }

    // Trigger shake Animation if error becomes true
    LaunchedEffect(isError) {
        if (isError) {
            repeat(4) {
                shakeOffset.animateTo(
                    targetValue = if (it % 2 == 0) 15f else -15f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
                )
            }
            shakeOffset.animateTo(0f)
        }
    }
    Row(
        modifier = Modifier
            .offset(x = shakeOffset.value.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxLength) { index ->
            val isFilled = index < pin.length

            val borderColor by animateColorAsState(
                targetValue = when {
                    isError -> MaterialTheme.colorScheme.error
                    isFilled -> Color(0xFF022A04) // Green
                    else -> Color.Gray.copy(alpha = 0.5f)
                },
                animationSpec = tween(300)
            )
            val backgroundColor by animateColorAsState(
                targetValue = if (isFilled && !isError) Color(0xFF022A04).copy(alpha = 0.15f)
                else Color.Transparent,
                animationSpec = tween(300)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(50.dp) // Rectangular frame size
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(2.dp, borderColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isFilled) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(
                                backgroundColor
                            )
                    )
                }
            }
        }
    }
}
