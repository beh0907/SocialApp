package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.SharedRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CustomAlertDialog(
    title: String = "",
    message: String = "",
    confirmText: String = stringResource(SharedRes.strings.dialog_confirm),
    dismissText: String = stringResource(SharedRes.strings.dialog_cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onConfirm()
                }
            ) {
                Text(
                    text = confirmText,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = dismissText,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}