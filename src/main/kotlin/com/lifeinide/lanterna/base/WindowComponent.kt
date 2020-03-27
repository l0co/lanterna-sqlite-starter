package com.lifeinide.lanterna.base

import com.googlecode.lanterna.gui2.AbstractInteractableComponent
import com.googlecode.lanterna.gui2.Window

/**
 * @author Lukasz Frankowski
 */
abstract class WindowComponent: BasicComponent() {

    protected var focusComponent: AbstractInteractableComponent<*>? = null

    val window: Window by lazy {
        buildWindow().apply {
            component = this@WindowComponent.component
            focusComponent?.takeFocus()
        }
    }

    protected abstract fun buildWindow(): Window

}

