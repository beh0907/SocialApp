package com.skymilk.socialapp.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.skymilk.socialapp.android.store.presentation.util.Event
import com.skymilk.socialapp.android.store.presentation.util.EventBus.events
import com.skymilk.socialapp.android.store.presentation.navigation.SocialApp
import com.skymilk.socialapp.android.ui.theme.SocialAppTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialAppTheme {
                SetObserveMessage()

                SocialApp()
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