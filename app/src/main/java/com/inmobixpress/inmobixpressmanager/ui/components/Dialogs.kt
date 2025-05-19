package com.inmobixpress.inmobixpressmanager.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.inmobixpress.inmobixpressmanager.ui.utils.dateToString
import com.inmobixpress.inmobixpressmanager.ui.utils.hourToMillis
import com.inmobixpress.inmobixpressmanager.ui.utils.today
import com.inmobixpress.inmobixpressmanager.ui.utils.validateDayOfWeek
import com.inmobixpress.inmobixpressmanager.ui.utils.year
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import java.util.Calendar

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    viewModel: MainViewModel,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    val dateState = rememberDatePickerState(
        selectableDates =
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return validateDayOfWeek(utcTimeMillis)
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year >= year()
            }
        }
    )
    val dateToString = dateState.selectedDateMillis?.let {
        dateToString(it.plus(hourToMillis(hours = 5)))
    } ?: today()
    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = {
                    //viewModel.onVisitDayChanged(dateToString)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        /*viewModel.onVisitLocalChanged(
                            visitLocal = dateState.selectedDateMillis?.plus(hourToMillis(hours = 5))
                                .millisToLocalDateTime()
                                .toKotlinLocalDateTime()
                        )*/
                        viewModel.onVisitMillisChanged(
                            visitMillis = dateState.selectedDateMillis!!.plus(
                                hourToMillis(hours = 5)
                            )
                        )
                    }
                    onConfirmation()
                }
            ) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRequest() }
            ) {
                Text(text = "Cancelar")
            }
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedTimePickerLauncher(
    viewModel: MainViewModel,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    /** Determines whether the time picker is dial or input */
    var showDial by remember { mutableStateOf(true) }

    /** The icon used for the icon button that switches from dial to input */
    val toggleIcon = if (showDial) {
        Icons.Filled.EditCalendar
    } else {
        Icons.Filled.AccessTime
    }

    AdvancedTimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
        toggle = {
            IconButton(onClick = { showDial = !showDial }) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = "Time picker type toggle",
                )
            }
        },
    ) {
        if (showDial) {
            TimePicker(
                state = timePickerState,
            )
        } else {
            TimeInput(
                state = timePickerState,
            )
        }
    }
}

@Composable
fun AdvancedTimePickerDialog(
    title: String = "Select Time",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
            Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    TextButton(onClick = onConfirm) { Text("Confirmar") }
                }
            }
        }
    }
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