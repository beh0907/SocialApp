package com.skymilk.socialapp.android.presentation.screen.auth.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.CustomTextField
import com.skymilk.socialapp.android.ui.theme.ButtonHeight
import com.skymilk.socialapp.android.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    uiState: SignUpUIState,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    //로그인 처리가 되었다면 이동
    LaunchedEffect(key1 = uiState.authenticationSuccess) {
        if (uiState.authenticationSuccess) onNavigateToHome()
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            CustomTextField(
                value = uiState.name,
                onValueChange = { text ->
                    onEvent(SignUpEvent.UpdateUserName(text))
                },
                hint = R.string.username_hint
            )

            CustomTextField(
                value = uiState.email,
                onValueChange = { text ->
                    onEvent(SignUpEvent.UpdateEmail(text))
                },
                hint = R.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = { text ->
                    onEvent(SignUpEvent.UpdatePassword(text))
                },
                hint = R.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            CustomTextField(
                value = uiState.passwordConfirm,
                onValueChange = { text ->
                    onEvent(SignUpEvent.UpdatePasswordConfirm(text))
                },
                hint = R.string.passwordConfirm_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onEvent(SignUpEvent.SignUp)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.signup_button_hint),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            CreateSection(onNavigateToSignIn = onNavigateToSignIn)
        }

        if (uiState.isAuthenticating) CircularProgressIndicator()
    }
}

@Composable
private fun ColumnScope.CreateSection(onNavigateToSignIn: () -> Unit) {
    TextButton(
        modifier = Modifier.align(alignment = CenterHorizontally),
        onClick = { onNavigateToSignIn() }) {

        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff94a3b8),
                    fontStyle = MaterialTheme.typography.labelMedium.fontStyle
                )
            ) {
                append("이미 가입된 회원이신가요?")
            }

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(" ")
                append("로그인")
            }
        })
    }
}