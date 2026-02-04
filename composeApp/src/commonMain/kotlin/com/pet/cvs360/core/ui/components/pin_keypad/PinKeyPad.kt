package com.pet.cvs360.core.ui.components.pin_keypad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cvs360.composeapp.generated.resources.Res
import cvs360.composeapp.generated.resources.backspace
import cvs360.composeapp.generated.resources.fingerprint_on
import org.jetbrains.compose.resources.painterResource

@Composable
fun PinKeyPad(
    isBiometricEnabled: Boolean,
    onNumberClick: (String) -> Unit,
    onBackSpace: () -> Unit,
    onBiometricClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "BIO", "0", "DEL")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(keys) { key ->
            when (key) {
                "BIO" -> {
                    if (isBiometricEnabled) {
                        IconButton(onClick = onBiometricClick) {
                            Icon(
                                painter = painterResource(Res.drawable.fingerprint_on),
                                modifier = Modifier
                                    .size(32.dp),
                                contentDescription = null
                            )
                        }
                    }
                }
                    "DEL" -> {
                        IconButton(onClick = onBackSpace) {
                            Icon(
                                painter = painterResource(Res.drawable.backspace),
                                modifier = Modifier
                                    .size(32.dp),
                                contentDescription = "Delete")
                        }
                    }
                else -> {
                    Button(
                        onClick = { onNumberClick(key) },
                        shape = CircleShape,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(text = key, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}
