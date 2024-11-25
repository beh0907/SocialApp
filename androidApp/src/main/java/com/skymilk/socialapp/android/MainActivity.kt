package com.skymilk.socialapp.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.skymilk.socialapp.android.presentation.util.Event
import com.skymilk.socialapp.android.presentation.util.EventBus.events
import com.skymilk.socialapp.android.presentation.navigation.SocialApp
import com.skymilk.socialapp.android.ui.theme.SocialAppTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialAppTheme {
                SetObserveMessage()

                val token = mainViewModel.authState.collectAsStateWithLifecycle(initialValue = null)
                SocialApp(token.value)
            }
        }
    }

    @Composable
    private fun SetObserveMessage() {
        //생명주기
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect(key1 = lifecycle) {
            //앱이 실행중일 경우에만 수집한다
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                //알림 이벤트 수집 시 처리
                events.collectLatest { event ->
                    when (event) {
                        is Event.Toast -> {
                            Toast.makeText(
                                this@MainActivity,
                                event.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Event.SnackBar -> {}
                    }
                }
            }
        }
    }
}