package com.example.langio.useful

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


enum class FlippableState {
    INITIALIZED,
    FRONT,
    BACK
}


@Composable
fun Flippable(

    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit,

    flipController: FlippableController,
    modifier: Modifier = Modifier,

    flipDurationMs: Int = 400,
    cameraDistance: Float = 30.0F,

    onFlippedListener: (currentSide: FlippableState) -> Unit = { _, -> },

) {
    var prevViewState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    var flippableState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    val transition: Transition<FlippableState> = updateTransition(
        targetState = flippableState,
        label = "Flip Transition",
    )



    LaunchedEffect(key1 = flipController, block = {
        flipController.flipRequests
            .onEach {
                prevViewState = flippableState
                flippableState = it
            }
            .launchIn(this)
    })

    val flipCall: () -> Unit = {
        if (transition.isRunning.not()) {
            prevViewState = flippableState
            if (flippableState == FlippableState.FRONT)
                flipController.flipToBack()
            else flipController.flipToFront()
        }
    }


    LaunchedEffect(key1 = transition.currentState, block = {
        if (transition.currentState == FlippableState.INITIALIZED) {
            prevViewState = FlippableState.INITIALIZED
            flippableState = FlippableState.FRONT
            return@LaunchedEffect
        }

        if (prevViewState != FlippableState.INITIALIZED && transition.currentState == flippableState) {
            onFlippedListener.invoke(flippableState)
        }
    })

    val frontRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        90f at flipDurationMs / 2
                        90f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        90f at 0
                        90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Rotation"
    ) { state ->
        when(state) {
            FlippableState.INITIALIZED, FlippableState.FRONT -> 0f
            FlippableState.BACK -> 180f
        }
    }

    val backRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        -90f at 0
                        -90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        -90f at flipDurationMs / 2
                        -90f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Rotation"
    ) { state ->
        when(state) {
            FlippableState.INITIALIZED, FlippableState.FRONT -> 180f
            FlippableState.BACK -> 0f
        }
    }

    val frontOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Opacity"
    ) { state ->
        when(state) {
            FlippableState.INITIALIZED, FlippableState.FRONT -> 1f
            FlippableState.BACK -> 0f
        }
    }

    val backOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FlippableState.FRONT isTransitioningTo FlippableState.BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                FlippableState.BACK isTransitioningTo FlippableState.FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Opacity"
    ) { state ->
        when(state) {
            FlippableState.INITIALIZED, FlippableState.FRONT -> 0f
            FlippableState.BACK -> 1f
        }
    }

    Box(
        modifier = modifier
            .clickable(
//                enabled = flipOnTouch,
                onClick = {
                    flipCall()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = cameraDistance
                rotationY = backRotation
            }
            .alpha(backOpacity)
            .zIndex(1F - backOpacity)
        ) {
            backSide()
        }

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = cameraDistance
                rotationY = frontRotation
            }
            .alpha(frontOpacity)
            .zIndex(1F - frontRotation)
        ) {
            frontSide()
        }
    }
}