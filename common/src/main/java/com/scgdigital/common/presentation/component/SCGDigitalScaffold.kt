package com.scgdigital.common.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.scgdigital.common.presentation.dialog.SCGDigitalDialogData
import com.scgdigital.common.presentation.theme.TransitionAnimation

data class SystemUiData(
    val color: Color,
    val darkIcon: Boolean,
)

enum class LoadingSize(val size: Dp) {
    TINY(100.dp),
    MEDIUM(150.dp),
    LARGE(240.dp)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SCGDigitalScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    fullScreenLoading: Boolean = false,
    shimmerLoading: Boolean = false,
    shimmer: @Composable () -> Unit = {},
    dialogData: SCGDigitalDialogData? = null,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    systemUiData: SystemUiData? = SystemUiData(Color.Transparent, true),
    loadingSize: LoadingSize = LoadingSize.MEDIUM,
    content: @Composable (PaddingValues) -> Unit
) {
    val isFullScreenDialog = dialogData is SCGDigitalDialogData.FullScreen
    val isNormalDialog = dialogData is SCGDigitalDialogData.Normal
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.run {
            systemUiData?.let {
                setSystemBarsColor(color = it.color, darkIcons = it.darkIcon)
                setNavigationBarColor(color = it.color, darkIcons = it.darkIcon)
            }
        }
    }
    val fullscreenDialogAlpha by animateFloatAsState(
        targetValue = if (isFullScreenDialog) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val normalDialogAlpha by animateFloatAsState(
        targetValue = if (isNormalDialog) 1f else 0f,
        animationSpec = tween(
            durationMillis = 90,
            easing = FastOutSlowInEasing
        )
    )
    val dialogScale by animateFloatAsState(
        targetValue = if (isNormalDialog || isFullScreenDialog) 1f else 0.6f,
        animationSpec = tween(
            durationMillis = 210,
            easing = FastOutSlowInEasing
        )
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (!isFullScreenDialog) 1f else 0f,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        )
    )

    Scaffold(
        modifier,
        scaffoldState,
        topBar.takeIf { !isFullScreenDialog } ?: {},
        bottomBar.takeIf { !isFullScreenDialog } ?: {},
        snackbarHost,
        floatingActionButton,
        floatingActionButtonPosition,
        isFloatingActionButtonDocked,
        drawerContent,
        drawerGesturesEnabled,
        drawerShape,
        drawerElevation,
        drawerBackgroundColor,
        drawerContentColor,
        drawerScrimColor,
        backgroundColor,
        contentColor,
    ) {
        Box(modifier = Modifier.alpha(contentAlpha)) {
            content(it)
        }
        when (dialogData) {
            is SCGDigitalDialogData.FullScreen -> FullScreenDialog(
                dialogData,
                Modifier
                    .scale(dialogScale)
                    .alpha(fullscreenDialogAlpha)
            )

            is SCGDigitalDialogData.Normal -> NormalDialog(
                dialogData,
                Modifier
                    .scale(dialogScale)
                    .alpha(normalDialogAlpha),
            )
            else -> {

            }
        }

        if (fullScreenLoading) {
            GeneralLoading(loadingSize)
        }

        AnimatedVisibility(
            visible = shimmerLoading,
            enter = EnterTransition.None,
            exit = TransitionAnimation.fadeOut
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsWithImePadding(),
            ) {
                shimmer()
            }
        }
    }
}

@Composable
fun GeneralLoading(loadingSize: LoadingSize) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.scgdigital.resource.R.raw.anim_general_loading))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsWithImePadding()
            .clickable(enabled = true) {
                // workaround to make all the components underneath unclickable
            },
    ) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .align(Alignment.Center)
                .width(loadingSize.size)
                .height(loadingSize.size)
        )
    }
}

@Preview
@Composable
fun PreviewTinyGeneralLoading() {
    GeneralLoading(loadingSize = LoadingSize.TINY)
}

@Preview
@Composable
fun PreviewMediumGeneralLoading() {
    GeneralLoading(loadingSize = LoadingSize.MEDIUM)
}

@Preview
@Composable
fun PreviewLargeGeneralLoading() {
    GeneralLoading(loadingSize = LoadingSize.LARGE)
}
