package com.scgdigital.common.presentation.theme

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
object TransitionAnimation {
    private const val TRANSITION_DURATION = 300

    private var screenDiffX = 75
    private var screenWidth = 320
    private var screenDiffY = 75
    private var screenHeight = 320

    fun setScreenSize(width: Int, height: Int) {
        screenWidth = width
        // Start point should be 20%
        screenDiffX = width / 5
        screenHeight = height
        // Start point should be 10%
        screenDiffY = height / 10
    }

    /** FadeThrough */
    val enterFadeThroughTransition = fadeIn() + expandIn()
    val exitFadeThroughTransition = scaleOut() + fadeOut()

    val fadeOut = fadeOut(
        animationSpec = tween(
            durationMillis = TRANSITION_DURATION,
            easing = FastOutSlowInEasing,
        )
    )

    val fadeInContent = fadeIn(initialAlpha = 0f, animationSpec = tween(90))

    val fadeInEnter = fadeIn(
        initialAlpha = 0f,
        animationSpec = tween(210, 90)
    )

    private val fadeOutExit = fadeOut(
        animationSpec = tween(90, 0, FastOutLinearInEasing)
    )

    private val fadeInPopEnter = fadeIn(
        animationSpec = tween(90, 0, FastOutLinearInEasing)
    )

    private val fadeOutPopExist = fadeOut(
        animationSpec = tween(210, 90)
    )

    /** Axis-Z */
    val enterTransitionAxisZ: EnterTransition =
        scaleIn(
            initialScale = 0.6f,
            animationSpec = tween(
                TRANSITION_DURATION,
                0,
                FastOutSlowInEasing
            )
        ) + fadeInEnter

    val exitTransitionAxisZ: ExitTransition =
        scaleOut(
            targetScale = 1.2f,
            animationSpec = tween(
                TRANSITION_DURATION,
                0,
                FastOutSlowInEasing
            )
        ) + fadeOutExit

    val popEnterTransitionAxisZ: EnterTransition =
        scaleIn(
            initialScale = 1.3f,
            animationSpec = tween(
                TRANSITION_DURATION,
                0,
                FastOutSlowInEasing
            )
        ) + fadeInPopEnter

    val popExitTransitionAxisZ: ExitTransition =
        scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(
                TRANSITION_DURATION,
                0,
                FastOutSlowInEasing
            )
        ) + fadeOutPopExist

    val fadeOutExitTransition: ExitTransition =
        fadeOut(
            animationSpec = tween(
                durationMillis = 90,
                easing = FastOutSlowInEasing,
            )
        )

    /** Axis-X */
    val enterTransitionAxisX: EnterTransition =
        slideInHorizontally(
            initialOffsetX = { screenDiffX },
            animationSpec = tween(TRANSITION_DURATION, 0, FastOutSlowInEasing)
        ) + fadeInEnter

    val exitTransitionAxisX: ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { -screenDiffX },
            animationSpec = tween(TRANSITION_DURATION, 0, FastOutSlowInEasing)
        ) + fadeOutExit

    val popEnterTransitionAxisX: EnterTransition =
        slideInHorizontally(
            initialOffsetX = { -screenDiffX },
            animationSpec = tween(TRANSITION_DURATION)
        ) + fadeInPopEnter

    val popExitTransitionAxisX: ExitTransition =
        slideOutHorizontally(
            targetOffsetX = { screenDiffX },
            animationSpec = tween(TRANSITION_DURATION)
        ) + fadeOutPopExist

    /** Axis-Y */
    val enterTransitionAxisY: EnterTransition =
        slideInVertically(
            initialOffsetY = { screenDiffY },
            animationSpec = tween(TRANSITION_DURATION, 0, FastOutSlowInEasing)
        ) + fadeInEnter

    val exitTransitionAxisY: ExitTransition =
        slideOutVertically(
            targetOffsetY = { -screenDiffY },
            animationSpec = tween(TRANSITION_DURATION, 0, FastOutSlowInEasing)
        ) + fadeOutExit

    val popEnterTransitionAxisY: EnterTransition =
        slideInVertically(
            initialOffsetY = { -screenDiffY },
            animationSpec = tween(TRANSITION_DURATION)
        ) + fadeInPopEnter

    val popExitTransitionAxisY: ExitTransition =
        slideOutVertically(
            targetOffsetY = { screenDiffY },
            animationSpec = tween(TRANSITION_DURATION)
        ) + fadeOutPopExist
}

@ExperimentalAnimationApi
public fun NavGraphBuilder.composableAxisX(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = { TransitionAnimation.enterTransitionAxisX },
        exitTransition = { TransitionAnimation.exitTransitionAxisX },
        popEnterTransition = { TransitionAnimation.popEnterTransitionAxisX },
        popExitTransition = { TransitionAnimation.popExitTransitionAxisX },
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}

@ExperimentalAnimationApi
public fun NavGraphBuilder.composableAxisY(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = { TransitionAnimation.enterTransitionAxisY },
        exitTransition = { TransitionAnimation.exitTransitionAxisY },
        popEnterTransition = { TransitionAnimation.popEnterTransitionAxisY },
        popExitTransition = { TransitionAnimation.popExitTransitionAxisY },
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}

@ExperimentalAnimationApi
public fun NavGraphBuilder.composableAxisZ(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = { TransitionAnimation.enterTransitionAxisZ },
        exitTransition = { TransitionAnimation.exitTransitionAxisZ },
        popEnterTransition = { TransitionAnimation.popEnterTransitionAxisZ },
        popExitTransition = { TransitionAnimation.popExitTransitionAxisZ },
        arguments = arguments,
        deepLinks = deepLinks,
        content = content
    )
}
