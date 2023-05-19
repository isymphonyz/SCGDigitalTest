package com.scgdigital.common.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogData
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogDataAction
import com.scgdigital.common.presentation.theme.SCGDigitalAndroidComposeTheme

enum class ButtonsOrientation {
    HORIZONTAL,
    VERTICAL
}

@Composable
fun NormalDialog(
    data: SCGDigitalDialogData.Normal,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = data.onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = data.autoDismiss,
            dismissOnClickOutside = data.autoDismiss
        ),
        title = {
            Text(
                text = data.title ?: stringResource(data.titleResId),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            )
        },
        text = {
            Text(
                text = data.message ?: stringResource(data.messageResId),
                style = MaterialTheme.typography.body2,
                lineHeight = 24.sp
            )
        },
        buttons = {
            // TODO: move the modifiers to FlowRow when it supports a modifier parameter
            Box(
                Modifier
                    .fillMaxWidth()
            ) {

                when (data.buttonsOrientation) {
                    // TODO: find the way to do auto layout because when screen size change the layout might break
                    ButtonsOrientation.HORIZONTAL -> {
                        Row(
                            modifier = Modifier
                                .align(alignment = Alignment.BottomEnd)
                                .padding(8.dp, 0.dp, 8.dp, 8.dp)
                        ) {
                            NormalDialogButtons(data)
                        }
                    }
                    ButtonsOrientation.VERTICAL -> {
                        Column(
                            modifier = Modifier
                                .align(alignment = Alignment.BottomEnd)
                                .padding(8.dp, 0.dp, 8.dp, 8.dp)
                        ) {
                            NormalDialogButtons(data)
                        }
                    }
                }
            }
        },
        shape = RoundedCornerShape(14.dp),
    )
}

@Composable
fun NormalDialogButtons(
    data: SCGDigitalDialogData.Normal
) {
    data.dismissAction?.let {
        TextButton(
            onClick = it.onClick,
            modifier = Modifier.padding(0.dp, 0.dp)
        ) {
            Text(
                text = it.text ?: stringResource(it.textResId),
                style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.padding(0.dp)
            )
        }
    }
    data.confirmAction.let {
        TextButton(
            onClick = it.onClick,
            modifier = Modifier.padding(0.dp, 0.dp)
        ) {
            Text(
                text = it.text ?: stringResource(it.textResId),
                style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun NormalDialogPreview() {
    SCGDigitalAndroidComposeTheme {
        Scaffold {
            NormalDialog(
                SCGDigitalDialogData.Normal(
                    title = "test",
                    message = "message",
                    confirmAction = SCGDigitalDialogDataAction("Retry") {},
                )
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun NormalDialogLongButtonPreview() {
    SCGDigitalAndroidComposeTheme {
        Scaffold {
            NormalDialog(
                SCGDigitalDialogData.Normal(
                    title = "test",
                    message = "message",
                    confirmAction = SCGDigitalDialogDataAction("Resume the Application") {},
                    dismissAction = SCGDigitalDialogDataAction("Cancel the Application") {},
                    buttonsOrientation = ButtonsOrientation.VERTICAL
                ),
            )
        }
    }
}
