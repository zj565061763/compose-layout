package com.sd.lib.compose.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode

interface FComposeLayoutApi {
    fun getLayoutCoordinates(tag: String): LayoutCoordinates?
}

@Composable
fun FComposeLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    check(LocalComposeLayout.current == null) {
        "Already in FComposeLayout scope."
    }

    val composeLayout = remember { ComposeLayout() }

    CompositionLocalProvider(
        LocalComposeLayout provides composeLayout,
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun fComposeLayoutApi(): FComposeLayoutApi {
    if (LocalInspectionMode.current) return ComposeLayout()
    return checkNotNull(LocalComposeLayout.current) {
        "Not in FComposeLayout scope."
    }
}

fun Modifier.fLayoutNode(tag: String): Modifier = composed {
    if (LocalInspectionMode.current) return@composed this
    if (tag.isEmpty()) error("tag is empty.")

    val layout = checkNotNull(LocalComposeLayout.current) {
        "Not in FComposeLayout scope."
    }

    DisposableEffect(layout, tag) {
        onDispose {
            layout.removeNode(tag)
        }
    }

    this.onGloballyPositioned {
        layout.addNode(tag, it)
    }
}