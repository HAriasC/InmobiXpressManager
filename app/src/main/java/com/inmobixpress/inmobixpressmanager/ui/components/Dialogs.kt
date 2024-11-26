package com.inmobixpress.inmobixpressmanager.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    isError: Boolean,
    confirmationText: String,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon, contentDescription = "", modifier = Modifier
                    .size(60.dp)
                    .fillMaxSize(1.0F)
            )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            if (isError) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = confirmationText)
                }
            } else {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(text = confirmationText)
                }
            }
        },
        dismissButton = {
            if (isError.not()) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        }
    )
}

@Composable
fun MessageDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    isError: Boolean,
    confirmationText: String,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon, contentDescription = "", modifier = Modifier
                    .size(60.dp)
                    .fillMaxSize(1.0F)
            )
        },
        title = {
            Text(text = dialogTitle, fontSize = 20.sp)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            if (isError) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = confirmationText)
                }
            } else {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(text = confirmationText)
                }
            }
        }
    )
}

@Preview
@Composable
fun MessageDialogPreview() {
    MessageDialog(
        onDismissRequest = { },
        onConfirmation = { },
        dialogTitle = "Lo sentimos, ocurrió un error",
        dialogText = "No pudimos continuar con el proceso que realizabas. Inténtalo nuevamente.",
        icon = Icons.Default.Warning,
        isError = true,
        confirmationText = "Entendido"
    )
}