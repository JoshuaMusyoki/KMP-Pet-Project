package com.pet.cvs360

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pet.cvs360.biometric.BiometricAuthorizationViewModel
import com.pet.cvs360.biometric.BiometricAuthScreen
import com.pet.cvs360.theme.Cvs360Theme

class MainActivity : FragmentActivity() {
    private val bioMetricUtil by lazy {
        BiometricUtilAndroidImpl(this, CipherUtilAndroidImpl())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        bioMetricUtil.preparePrompt(
            "Biometric",
            "Biometric Authentication",
            "This biometric is used for 2FA"
        )
        setContent {
            Cvs360Theme {
               // App()
                val biometricViewModel: BiometricAuthorizationViewModel = viewModel()
                BiometricAuthScreen(
                    bioMetricUtil = bioMetricUtil,
                    biometricViewModel = biometricViewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}