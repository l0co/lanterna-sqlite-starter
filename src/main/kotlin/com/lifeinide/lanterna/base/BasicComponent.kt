package com.lifeinide.lanterna.base

import com.googlecode.lanterna.gui2.Button
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

/**
 * @author Lukasz Frankowski
 */
abstract class BasicComponent {

    abstract val component: Component

    protected fun clickButton(button: Button) {
        if (button.isEnabled) {
            button.takeFocus()
            button.handleKeyStroke(KeyStroke(KeyType.Enter))
        }
    }

}

