package com.pet.cvs360.feature.onboarding.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pet.cvs360.core.ui.components.texts.PrimaryTextField
import cvs360.composeapp.generated.resources.Res
import cvs360.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun RegisterScreen(
    viewModel: OnboardingViewModel
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    modifier = Modifier
                        .size(60.dp),
                    contentDescription = null
                )

                Text(
                    text = "Welcome to SafiriTrack",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onIntent(OnboardingIntent.EmailChanged(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = "Email Address",

            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryTextField(
                value = state.pin,
                onValueChange = {
                    viewModel.onIntent(OnboardingIntent.PinChanged(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = "Create Pin/Password",
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryTextField(
                value = state.confirmPin,
                onValueChange = {
                    viewModel.onIntent(OnboardingIntent.ConfirmPinChanged(it))
                },
                label = "Password Confirmation",
                isError = !state.pinMatch
            )
            if (!state.pinMatch) {
                Text(
                    text = "ERROR: Password do not match!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.onIntent(OnboardingIntent.SubmitLogin)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp)
                    .imePadding(),
                enabled = state.pinMatch && state.email.isNotEmpty() && !state.isLoading) {
                if (state.isLoading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(text = "Create SafiriTrack Account")
                }
            }
        }
    }
}