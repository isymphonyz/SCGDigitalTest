package com.scgdigital.common.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogData
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogDataAction
import com.scgdigital.common.presentation.theme.SCGDigitalAndroidComposeTheme
import com.scgdigital.resource.R

@Composable
fun FullScreenDialog(
    data: SCGDigitalDialogData.FullScreen,
    modifier: Modifier = Modifier,
) {
    val title = data.title
    val message = data.message
    val messageResId = data.messageResId
    val imageResId = data.imageResId
    val action = data.action
    Column(modifier = modifier.navigationBarsPadding()) {
        Image(
            bitmap = ImageBitmap.imageResource(id = imageResId),
            contentDescription = "fullscreen image",
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(horizontal = 24.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = title ?: stringResource(data.titleResId),
            style = MaterialTheme.typography.h5.copy(MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
        )

        Text(
            text = message ?: stringResource(messageResId),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(36.dp))

        if (action != null) {
            Button(
                onClick = action.onClick,
                contentPadding = PaddingValues(
                    start = 24.dp,
                    top = 16.dp,
                    end = 24.dp,
                    bottom = 16.dp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = action.text ?: stringResource(id = action.textResId))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun FullScreenDialogPreview() {
    SCGDigitalAndroidComposeTheme {
        Scaffold {
            FullScreenDialog(
                SCGDigitalDialogData.FullScreen(
                    title = stringResource(id = R.string.general_dialog_network_error_title),
                    message = stringResource(id = R.string.general_dialog_network_error_description),
                    action = SCGDigitalDialogDataAction("Refresh") {
                    },
                )
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(name = "smallScreen", widthDp = 320, heightDp = 320)
@Composable
fun FullScreenDialogPreviewSmallScreen() {
    SCGDigitalAndroidComposeTheme {
        Scaffold {
            FullScreenDialog(
                SCGDigitalDialogData.FullScreen(
                    title = stringResource(id = R.string.general_dialog_network_error_title),
                    message = stringResource(id = R.string.general_dialog_network_error_description),
                    action = SCGDigitalDialogDataAction(stringResource(R.string.global_button_refresh)) {
                    },
                )
            )
        }
    }
}
