package com.lifeinide.lanterna.service

import com.googlecode.lanterna.gui2.AnimatedLabel

/**
 * Works with `ExecutorService` to provide animated throbber on the status bar.
 *
 * @author Lukasz Frankowski
 */
object ThrobberService {

    val throbber: AnimatedLabel = AnimatedLabel.createClassicSpinningLine()
    @Volatile private var startCounter: Int = 0

    init {
        throbber.stopAnimation()
    }

    fun start() {
        if (startCounter++==0)
            throbber.startAnimation(100)
    }

    fun stop() {
        if (--startCounter ==0)
            throbber.stopAnimation()
    }

}
