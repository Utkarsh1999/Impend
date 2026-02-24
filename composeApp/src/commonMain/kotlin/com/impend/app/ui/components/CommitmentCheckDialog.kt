package com.impend.app.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.impend.app.ui.theme.ImpendColors
import com.impend.shared.domain.model.Pledge

@Composable
fun CommitmentCheckDialog(
    pledge: Pledge,
    onConfirmBreak: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Commitment Check",
                style = MaterialTheme.typography.h2,
                color = ImpendColors.Error
            )
        },
        text = {
            Text(
                "You pledged to avoid ${pledge.category} until the commitment period ends. Are you sure you want to break this pledge?",
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmBreak,
                colors = ButtonDefaults.buttonColors(backgroundColor = ImpendColors.Error)
            ) {
                Text("I broke my pledge", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("I'll stick to it", color = ImpendColors.Primary)
            }
        },
        shape = MaterialTheme.shapes.medium,
        backgroundColor = Color.White
    )
}
