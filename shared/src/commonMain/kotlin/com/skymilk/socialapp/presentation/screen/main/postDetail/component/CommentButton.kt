package com.skymilk.socialapp.presentation.screen.main.postDetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.ui.theme.LargeSpacing
import dev.icerock.moko.resources.compose.stringResource

//전송 버튼
@Composable
fun SendCommentButton(
    modifier: Modifier = Modifier,
    sendCommentEnabled: Boolean,
    onSendClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val border = if (sendCommentEnabled) null
    else BorderStroke(
        1.dp,
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    )

    TextButton(
        modifier = modifier.height(35.dp),
        enabled = sendCommentEnabled,
        onClick = {
            //키보드 숨기기
            keyboardController?.hide()

            //전송 이벤트
            onSendClick()
        },
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        ),
        border = border,
        shape = RoundedCornerShape(percent = 50),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = LargeSpacing),
            text = stringResource(SharedRes.strings.send_comment_button),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}