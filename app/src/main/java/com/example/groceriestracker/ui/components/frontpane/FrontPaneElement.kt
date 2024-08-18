package com.example.groceriestracker.ui.components.frontpane

interface FrontPaneElement {
    var isVisible: Boolean

    fun hide() {
        isVisible = false
    }

    fun show() {
        isVisible = true
    }
}