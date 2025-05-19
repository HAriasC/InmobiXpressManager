package com.inmobixpress.inmobixpressmanager

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.inmobixpress.inmobixpressmanager.ui.navigation.GlobalNavigation
import com.inmobixpress.inmobixpressmanager.ui.theme.InmobiXpressManagerTheme
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.LoginViewModel
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val darkTheme = isSystemInDarkTheme()
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }
            InmobiXpressManagerTheme {
                GlobalNavigation(loginViewModel = loginViewModel, mainViewModel = mainViewModel)
            }
        }
        authAAnonymously()
        askNotificationPermission()
        registerDevice()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {

        } else {

        }
    }

    private fun registerDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            /*val msg = getString(R.string.msg_token_fmt, token)
            Log.d("FCM", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()*/
        })
    }

    private fun authAAnonymously() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        auth.signInAnonymously().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "signInAnonymously:success")
                val user = auth.currentUser
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInAnonymously:failure", task.exception)
            }
        }
    }
}


private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)