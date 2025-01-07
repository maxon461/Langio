package com.example.langio.useful

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class FlippableController {

    private var _currentSide: FlippableState = FlippableState.FRONT
    private var _flipEnabled: Boolean = true

    private val _flipRequests = MutableSharedFlow<FlippableState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()




    fun flipToFront() {
        flip(FlippableState.FRONT)
    }
    fun flipToBack() {
        flip(FlippableState.BACK)
    }
    fun flip(flippableState: FlippableState) {
        if (_flipEnabled.not())
            return

        _currentSide = flippableState
        _flipRequests.tryEmit(flippableState)
    }

    fun flip() {
        if (_currentSide == FlippableState.FRONT)
            flipToBack()
        else flipToFront()
    }
    fun getCurrentSide(): FlippableState {
        return _currentSide
    }
}


@Composable
fun rememberFlipController(): FlippableController {
    return remember { FlippableController() }
}