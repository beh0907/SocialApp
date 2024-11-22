package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.skymilk.socialapp.android.R
import com.skymilk.socialapp.android.store.presentation.common.CustomTextField
import com.skymilk.socialapp.android.ui.theme.ButtonHeight
import com.skymilk.socialapp.android.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing

@Destination
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    uiState: SignInUIState,
    onEvent: (SignInEvent) -> Unit,
) {
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LargeSpacing)
    ) {

        CustomTextField(
            value = uiState.email,
            onValueChange = { text ->
                onEvent(SignInEvent.UpdateEmail(text))
            },
            hint = R.string.email_hint,
            keyboardType = KeyboardType.Email
        )

        CustomTextField(
            value = uiState.password,
            onValueChange = { text ->
                onEvent(SignInEvent.UpdatePassword(text))
            },
            hint = R.string.password_hint,
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
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.signin_button_hint))
        }
    }
}