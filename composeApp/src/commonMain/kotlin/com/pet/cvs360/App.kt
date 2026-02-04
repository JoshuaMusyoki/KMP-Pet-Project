package com.pet.cvs360

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pet.cvs360.biometric.BioMetricUtil
import com.pet.cvs360.biometric.BiometricAuthorizationViewModel
import com.pet.cvs360.feature.onboarding.sign_up.OnboardingViewModel
import com.pet.cvs360.feature.onboarding.sign_up.RegisterScreen
import com.pet.cvs360.theme.Cvs360Theme
import kotlin.getValue

@Composable
@Preview
fun App() {

    Cvs360Theme {

        Scaffold (
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val viewModel = OnboardingViewModel()
            val biometricViewModel = BiometricAuthorizationViewModel()
            RegisterScreen(
                viewModel,
            )
        }
    }
}
//2025@Dev25!