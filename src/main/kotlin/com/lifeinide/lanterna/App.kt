package com.lifeinide.lanterna

import com.lifeinide.lanterna.db.Db

/**
 * @author Lukasz Frankowski
 */
class App {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello world") // TODOLF remove

            if (Db.empty)
                Db.populateTestData()
        }

    }

}
