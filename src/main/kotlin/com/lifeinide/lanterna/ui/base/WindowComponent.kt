package com.lifeinide.lanterna.ui.base

import com.googlecode.lanterna.gui2.AbstractInteractableComponent
import com.googlecode.lanterna.gui2.Window

/**
 * A base window builder.
 *
 * @author Lukasz Frankowski
 */
abstract class WindowComponent: BasicComponent() {

    /** The component which gains focus by default when the window is displayed **/
    protected var focusComponent: AbstractInteractableComponent<*>? = null

    val window: Window by lazy {
        buildWindow().apply {
            component = this@WindowComponent.component
            focusComponent?.takeFocus()
        }
    }

    /** Should build the window **/
    protected abstract fun buildWindow(): Window

}

