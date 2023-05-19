package com.scgdigital.common.presentation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.scgdigital.common.presentation.component.ButtonsOrientation
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.resource.R

sealed class SCGDigitalDialogData {

    abstract val title: String?
    abstract val titleResId: Int
    abstract val message: String?
    abstract val messageResId: Int
    abstract val autoDismiss: Boolean
    abstract val buttonsOrientation: ButtonsOrientation

    data class Normal(
        override val title: String? = null,
        override val titleResId: Int = R.string.app_name,
        override val message: String? = null,
        override val messageResId: Int = R.string.app_name,
        override val autoDismiss: Boolean = true,
        override val buttonsOrientation: ButtonsOrientation = ButtonsOrientation.HORIZONTAL,
        val confirmAction: SCGDigitalDialogDataAction,
        val dismissAction: SCGDigitalDialogDataAction? = null,
        val onDismiss: () -> Unit = {},
    ) : SCGDigitalDialogData()

    data class FullScreen(
        override val title: String? = null,
        override val titleResId: Int = R.string.app_name,
        override val message: String? = null,
        override val messageResId: Int = R.string.app_name,
        override val autoDismiss: Boolean = true,
        override val buttonsOrientation: ButtonsOrientation = ButtonsOrientation.HORIZONTAL,
        val imageResId: Int = R.drawable.ic_full_screen_error,
        val action: SCGDigitalDialogDataAction? = null,
    ) : SCGDigitalDialogData()
}

data class SCGDigitalDialogDataAction(
    val text: String? = null,
    val textResId: Int = R.string.app_name,
    val onClick: () -> Unit = {},
)

@Composable
fun SCGDigitalError.toDefaultDialogData(
    onDismiss: () -> Unit,
) = when (this) {
    SCGDigitalError.Common -> SCGDigitalDialogData.Normal(
        title = stringResource(R.string.global_dialog_headline_oops),
        message = stringResource(R.string.global_dialog_body_ran_into_issue),
        confirmAction = SCGDigitalDialogDataAction(
            text = stringResource(R.string.global_dialog_button_retry),
            onClick = onDismiss,
        ),
        onDismiss = onDismiss,
    )
    SCGDigitalError.Network -> SCGDigitalDialogData.FullScreen(
        title = stringResource(R.string.global_headline_full_screen_error_network),
        message = stringResource(R.string.global_subtitle_full_screen_error_network),
        action = SCGDigitalDialogDataAction(
            text = stringResource(R.string.global_button_refresh),
            onClick = onDismiss,
        )
    )
}

@Composable
fun SCGDigitalError.toDialogData(
    normalOnDismiss: () -> Unit = {},
    normalConfirmAction: SCGDigitalDialogDataAction? = null,
    normalDismissAction: SCGDigitalDialogDataAction? = null,
    fullScreenAction: SCGDigitalDialogDataAction? = null,
) = when (this) {
    SCGDigitalError.Common -> SCGDigitalDialogData.Normal(
        title = stringResource(R.string.global_dialog_headline_oops),
        message = stringResource(R.string.global_dialog_body_ran_into_issue),
        confirmAction = normalConfirmAction ?: SCGDigitalDialogDataAction(
            text = stringResource(R.string.global_dialog_button_retry),
            onClick = normalOnDismiss,
        ),
        dismissAction = normalDismissAction,
        onDismiss = normalOnDismiss,
    )
    SCGDigitalError.Network -> fullScreenNetworkErrorDialogData(fullScreenAction)
}

fun generalErrorDialogData(
    normalOnDismiss: () -> Unit = {},
    normalConfirmAction: SCGDigitalDialogDataAction? = null,
    normalDismissAction: SCGDigitalDialogDataAction? = null,
) = SCGDigitalDialogData.Normal(
    titleResId = R.string.global_dialog_headline_oops,
    messageResId = R.string.global_dialog_body_ran_into_issue,
    confirmAction = normalConfirmAction ?: SCGDigitalDialogDataAction(
        textResId = R.string.global_dialog_button_retry,
        onClick = normalOnDismiss,
    ),
    dismissAction = normalDismissAction,
    onDismiss = normalOnDismiss,
)

@Composable
fun fullScreenGeneralErrorDialogData(onDismiss: () -> Unit) = SCGDigitalDialogData.FullScreen(
    title = stringResource(R.string.global_headline_full_screen_error_network),
    message = stringResource(R.string.global_subtitle_full_screen_error_general),
    action = SCGDigitalDialogDataAction(
        text = stringResource(R.string.global_button_refresh),
        onClick = onDismiss,
    )
)

fun fullScreenGeneralErrorDialogDataRes(onClick: () -> Unit) = SCGDigitalDialogData.FullScreen(
    titleResId = R.string.global_headline_full_screen_error_network,
    messageResId = R.string.global_subtitle_full_screen_error_general,
    action = SCGDigitalDialogDataAction(
        textResId = R.string.global_button_refresh,
        onClick = onClick,
    )
)

@Composable
fun fullScreenNetworkErrorDialogData(onDismiss: () -> Unit) = SCGDigitalDialogData.FullScreen(
    title = stringResource(R.string.global_headline_full_screen_error_network),
    message = stringResource(R.string.global_subtitle_full_screen_error_network),
    action = SCGDigitalDialogDataAction(
        text = stringResource(R.string.global_button_refresh),
        onClick = onDismiss,
    )
)

fun fullScreenNetworkErrorDialogDataRes(onClick: () -> Unit) = SCGDigitalDialogData.FullScreen(
    titleResId = R.string.global_headline_full_screen_error_network,
    messageResId = R.string.global_subtitle_full_screen_error_network,
    action = SCGDigitalDialogDataAction(
        textResId = R.string.global_button_refresh,
        onClick = onClick,
    )
)

@Composable
fun fullScreenNetworkErrorDialogData(action: SCGDigitalDialogDataAction? = null) =
    SCGDigitalDialogData.FullScreen(
        title = stringResource(R.string.global_headline_full_screen_error_network),
        message = stringResource(R.string.global_subtitle_full_screen_error_network),
        action = action
    )
