package com.pet.cvs360.feature.onboarding.biometric.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pet.cvs360.biometric.BioMetricUtil
import com.pet.cvs360.biometric.BiometricAuthorizationViewModel
import cvs360.composeapp.generated.resources.Res
import cvs360.composeapp.generated.resources.biometric_authentication
import org.jetbrains.compose.resources.painterResource

@Composable
fun VerifyBiometric(
    modifier: Modifier = Modifier,
    biometricAuthorizationViewModel: BiometricAuthorizationViewModel,
    bioMetricUtil: BioMetricUtil
) {
    val biometricState by biometricAuthorizationViewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.biometric_authentication),
            contentDescription = "biometric_authentication"
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            biometricAuthorizationViewModel.authenticateBiometric(bioMetricUtil)
        },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Verify Biometric",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(all = 12.dp)
            )
        }
        biometricState.error?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(all = 12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}