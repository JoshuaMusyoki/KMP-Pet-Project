package com.pet.cvs360.feature.onboarding.sign_up

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pet.cvs360.biometric.BioMetricUtil
import com.pet.cvs360.biometric.BiometricAuthorizationViewModel
import com.pet.cvs360.core.ui.components.pin_keypad.PinDots
import com.pet.cvs360.core.ui.components.pin_keypad.PinKeyPad
import com.pet.cvs360.core.ui.components.texts.PrimaryTextField
import cvs360.composeapp.generated.resources.Res
import cvs360.composeapp.generated.resources.bike_lane
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegisterScreen(
    viewModel: OnboardingViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isConfirming = state.pin.length == 4
    val isComplete = state.pinMatch && state.confirmPin.length == 4
    val showPinKeyPad = state.email.isNotEmpty() && !isComplete
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            4.dp,
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(16.dp)
                        )
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.bike_lane),
                        modifier = Modifier
                            .size(60.dp),
                        contentDescription = "Bike Lane"
                    )
                }
                Text(
                    text = "Welcome to SafiriTrack",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Input Section
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PrimaryTextField(
                    value = state.email,
                    onValueChange = { viewModel.onIntent(OnboardingIntent.EmailChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = "Email Address",
                )

                AnimatedContent(targetState = if (state.pin.length < 4) "Create PIN" else "Confirm PIN") { label ->
                    Text(text = label, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))

                PinDots(
                    pin = if (state.pin.length < 4) state.pin else state.confirmPin,
                    isError = !state.pinMatch && state.confirmPin.length == 4
                )

                Spacer(modifier = Modifier.weight(1f))
                /*Spacer(modifier = Modifier.height(16.dp))
                 //if (!isConfirming) {
                    PrimaryTextField(
                        value = state.pin,
                        onValueChange = {},
                        readOnly = true,
                        visualTransformation = PasswordVisualTransformation(),
                        label = "Create Pin",
                    )
                //} else {
                    PrimaryTextField(
                        value = state.confirmPin,
                        onValueChange = {},
                        readOnly = true,
                        visualTransformation = PasswordVisualTransformation(),
                        label = "Pin Confirmation",
                        isError = !state.pinMatch
                    )
                    if (!state.pinMatch && state.confirmPin.length == 4) {
                        Text(
                            text = "Error: Pins do not match!",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    //}
                //}*/
                // }

                // Keypad Section (Fixed at bottom)
                AnimatedVisibility(
                    visible = showPinKeyPad,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    PinKeyPad(
                        isBiometricEnabled = state.isBiometricEnabled,
                        onNumberClick = { digit ->
                            if (!isConfirming) {
                                if (state.pin.length < 4) viewModel.onIntent(
                                    OnboardingIntent.PinChanged(
                                        state.pin + digit
                                    )
                                )
                            } else {
                                if (state.confirmPin.length < 4) viewModel.onIntent(
                                    OnboardingIntent.ConfirmPinChanged(state.confirmPin + digit)
                                )
                            }
                        },
                        onBackSpace = {
                            if (isConfirming && state.confirmPin.isEmpty()) {
                                // Logic to go back to editing the first PIN if empty
                                viewModel.onIntent(
                                    OnboardingIntent.PinChanged(
                                        state.pin.dropLast(
                                            1
                                        )
                                    )
                                )
                            } else if (isConfirming) {
                                viewModel.onIntent(
                                    OnboardingIntent.ConfirmPinChanged(
                                        state.confirmPin.dropLast(
                                            1
                                        )
                                    )
                                )
                            } else {
                                viewModel.onIntent(
                                    OnboardingIntent.PinChanged(
                                        state.pin.dropLast(
                                            1
                                        )
                                    )
                                )
                            }
                        },
                        onBiometricClick = { viewModel.onIntent(OnboardingIntent.BiometricLoginRequested) }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Action Button
                AnimatedVisibility(visible = isComplete) {
                    if (state.pinMatch && state.confirmPin.length == 4) {
                        Button(
                            onClick = {
                                viewModel.onIntent(OnboardingIntent.SubmitLogin)
                                      },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(56.dp),
                            enabled = state.pinMatch && state.confirmPin.length == 4 && state.email.isNotEmpty() && !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(text = "Create SafiriTrack Account")
                            }
                        }
                    }
                }
            }
        }
    }
}