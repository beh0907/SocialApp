package com.skymilk.socialapp.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.skymilk.socialapp.MainAuthState
import com.skymilk.socialapp.MainViewModel
import com.skymilk.socialapp.store.presentation.navigation.SocialApp
import com.skymilk.socialapp.store.presentation.util.EventBus.messageEvents
import com.skymilk.socialapp.store.presentation.util.MessageEvent
import com.skymilk.socialapp.ui.theme.SocialAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var authState: MainAuthState by mutableStateOf(MainAuthState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.authState.collect {
                    authState = it
                }
            }
        }

        //데이터 스토어로부터 유저 정보를 가져올 때까지 로딩
        splashScreen.setKeepOnScreenCondition {
            authState !is MainAuthState.Success
        }

        setContent {
            SocialAppTheme {
                SocialApp(authState)
            }
        }
    }
}