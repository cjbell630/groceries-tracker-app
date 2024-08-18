package com.example.groceriestracker.ui.components

interface FrontPaneElement {
    var isVisible: Boolean

    fun hide() {
        isVisible = false
    }

    fun show() {
        isVisible = true
    }
}