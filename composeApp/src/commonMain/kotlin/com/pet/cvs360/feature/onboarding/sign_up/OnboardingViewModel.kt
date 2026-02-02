package com.pet.cvs360.feature.onboarding.sign_up

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class OnboardingViewModel: ViewModel() {
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    private val _effect = Channel<OnboardingEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.EmailChanged -> _state.update {
                it.copy(email = intent.email)
            }

            is OnboardingIntent.PinChanged -> _state.update {
                it.copy(pin = intent.pin)
            }

            is OnboardingIntent.ConfirmPinChanged -> _state.update {
                it.copy(confirmPin = intent.confirmPin)
            }

            OnboardingIntent.SubmitLogin -> performLogin()

            OnboardingIntent.BiometricLoginRequested -> {
                viewModelScope.launch {
                    _effect.send(OnboardingEffect.TriggerBiometrics)
                }
            }
            // Add specific sme intents here
            is OnboardingIntent.BusinessDetailsEntered -> {
                _state.update {
                    it.copy(
                        businessName = intent.businessName,
                        step = OnboardingStep.Summary
                    )
                }
            }
        }
    }

    private fun performLogin() {
        _state.update {
            it.copy(isLoading = true)
        }

        // Simulate API Call
        viewModelScope.launch {
            delay(1500)
            _state.update {
                it.copy(isLoading = true, step = OnboardingStep.Permissions)
            }
        }
    }
}