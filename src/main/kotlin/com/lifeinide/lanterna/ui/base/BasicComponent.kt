package com.lifeinide.lanterna.ui.base

import com.googlecode.lanterna.gui2.Button
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

/**
 * A base component holder.
 *
 * @author Lukasz Frankowski
 */
abstract class BasicComponent {

    /**
     * Should provide the component. Usually with `override val component: Component by lazy {...}`
     */
    abstract val component: Component

    /**
     * Simulates button click
     */
    protected fun clickButton(button: Button) {
        if (button.isEnabled) {
            button.takeFocus()
            button.handleKeyStroke(KeyStroke(KeyType.Enter))
        }
    }

}

