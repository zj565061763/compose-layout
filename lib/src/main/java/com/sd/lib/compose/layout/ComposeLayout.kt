package com.sd.lib.compose.layout

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.layout.LayoutCoordinates

internal val LocalComposeLayout = staticCompositionLocalOf<ComposeLayout?> { null }

internal fun ComposeLayout(): ComposeLayout {
    return ComposeLayoutImpl()
}

internal interface ComposeLayout : FComposeLayoutApi {
    fun addNode(tag: String, layoutCoordinates: LayoutCoordinates)
    fun removeNode(tag: String)
}

private class ComposeLayoutImpl : ComposeLayout {
    private val _nodes = mutableMapOf<String, LayoutCoordinates>()

    override fun addNode(tag: String, layoutCoordinates: LayoutCoordinates) {
        _nodes[tag] = layoutCoordinates
    }

    override fun removeNode(tag: String) {
        _nodes.remove(tag)
    }

    override fun getLayoutCoordinates(tag: String): LayoutCoordinates? {
        return _nodes[tag]
    }
}