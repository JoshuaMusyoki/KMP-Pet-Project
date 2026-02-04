package com.pet.cvs360.feature.onboarding.sign_up

data class OnboardingUiState(
    val step: OnboardingStep = OnboardingStep.Credentials,
    val email: String = "",
    val pin: String = "",
    val confirmPin: String = "",
    val isBiometricEnabled: Boolean = false,
    val businessName: String = "",
    val tillNumber: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isComplete: Boolean = false,
) {
    val pinMatch: Boolean
        get() = pin == confirmPin
}

sealed class OnboardingStep {
    object Credentials: OnboardingStep()
    object Permissions: OnboardingStep()
    object BusinessSetup: OnboardingStep()
    object Summary: OnboardingStep()
}

sealed class OnboardingEffect {
    object TriggerBiometrics : OnboardingEffect()
}

sealed class OnboardingIntent {
    data class EmailChanged(val email: String) : OnboardingIntent()
    data class PinChanged(val pin: String) : OnboardingIntent()

    data class ConfirmPinChanged(val confirmPin: String) : OnboardingIntent()
    object SubmitLogin : OnboardingIntent()
    object BiometricLoginRequested : OnboardingIntent()
    data class BusinessDetailsEntered(val businessName: String) : OnboardingIntent()
}
