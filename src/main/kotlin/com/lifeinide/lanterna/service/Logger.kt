package com.lifeinide.lanterna.service

import java.time.Instant

/**
 * @author Lukasz Frankowski
 */
class Logger {

    companion object {

        var loggingEnabled = false

        fun log(s: String) {
            if (loggingEnabled)
                println("[${Thread.currentThread().name}] ${Instant.now()}: $s")
        }

        fun log(s: String, t: Throwable) {
            if (loggingEnabled) {
                log(s)
                t.printStackTrace()
            }
        }

    }

}
