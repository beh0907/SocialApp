package com.skymilk.socialapp.android.presentation.common.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.android.ui.theme.White

@Composable
fun FollowsButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onFollowClick: () -> Unit,
    isOutline: Boolean = false
) {
    TextButton(
        modifier = modifier.heightIn(30.dp),
        onClick = { onFollowClick() },
        colors = if (isOutline) ButtonDefaults.outlinedButtonColors() else ButtonDefaults.buttonColors(),
        border = if (isOutline) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodySmall,
            color = White,
            fontWeight = FontWeight.SemiBold
        )
    }
}