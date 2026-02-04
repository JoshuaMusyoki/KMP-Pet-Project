package com.pet.cvs360.biometric
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BiometricAuthorizationViewModel: ViewModel() {
    private val setBiometricPublicKeyRepository = SetBiometricPublicKeyRepository()
    private val verifyBiometric = VerifyBiometric()

    private val _state: MutableStateFlow<BiometricState> =
        MutableStateFlow(BiometricState(false, null))

    private val _effect: MutableSharedFlow<BiometricEffect> =
        MutableSharedFlow(replay = 0)

    val state: StateFlow<BiometricState>
        get() = _state

    val effect: SharedFlow<BiometricEffect>
        get() = _effect

    fun setBiometricAuthorization(biometricUtil: BioMetricUtil) {
        viewModelScope.launch {
            _state.value = BiometricState(isLoading = true, error = null)

            if (!biometricUtil.canAuthenticate()) {
                _state.value = BiometricState(isLoading = true, error = "Biometric not available")
                return@launch
            }

            val publicKey = biometricUtil.setAndReturnPublicKey() ?: ""
            setBiometricPublicKeyRepository.setBiometricPublicKey(publicKey)

            _state.value = BiometricState(isLoading = true, error = null)
            _effect.emit(BiometricEffect.BiometricSetSuccess)
        }
    }

    fun authenticateBiometric(biometricUtil: BioMetricUtil) {
        viewModelScope.launch {
            when(val biometricResult = biometricUtil.authenticate()) {
                AuthenticationResult.AttemptExhausted -> {
                    _state.value = BiometricState(isLoading = false, error = "Attempt Exhausted")
                }

                is AuthenticationResult.Error -> {
                    _state.value = BiometricState(isLoading = false, error = biometricResult.error)
                }

                AuthenticationResult.Failed -> {
                    _state.value =
                        BiometricState(isLoading = false, error = "Biometric Failed")
                }

                AuthenticationResult.NegativeButtonClick -> {
                    _state.value =
                        BiometricState(isLoading = false, error = "Biometric Cancelled")
                }

                AuthenticationResult.Success -> {
                    _state.value = BiometricState(isLoading = false, error = null)

                    val signedUserId = biometricUtil.signUserId("userId")
                    val result = verifyBiometric.verifyBiometricPublicKey(signedUserId)

                    if (result.isSuccess) {
                        _state.value = BiometricState(isLoading = true, error = null)
                        _effect.emit(BiometricEffect.BiometricAuthSuccess)
                    } else {
                        _state.value = BiometricState(isLoading = false, error = result.exceptionOrNull()!!.message)
                    }
                }
            }
        }
    }
}