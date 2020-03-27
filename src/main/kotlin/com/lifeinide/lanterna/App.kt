package com.lifeinide.lanterna

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
import com.lifeinide.lanterna.db.Db
import com.lifeinide.lanterna.service.ExecutorService
import com.lifeinide.lanterna.service.Logger
import com.lifeinide.lanterna.service.ThrobberService
import com.lifeinide.lanterna.ui.BrowserHelper
import java.awt.Dimension

/**
 * @author Lukasz Frankowski
 */
object App {

    // a size adjustment when the app is displayed in the swing frame
    const val SWING_WIDTH = 1500
    const val SWING_HEIGHT = 800

    lateinit var terminalFactory: DefaultTerminalFactory
    lateinit var screen: TerminalScreen
    lateinit var windowManager: DefaultWindowManager
    lateinit var textGUI: MultiWindowTextGUI

    lateinit var backgroundPanel: Panel
    lateinit var footerPanel: Panel
    lateinit var footerLabel: Label

    @JvmStatic
    fun main(args: Array<String>) {
        terminalFactory = DefaultTerminalFactory()
        screen = terminalFactory.createScreen()
        screen.startScreen()

        backgroundPanel = Panel(BorderLayout())
        backgroundPanel.addComponent(EmptySpace(TextColor.ANSI.BLUE), BorderLayout.Location.CENTER)

        footerPanel = Panel(GridLayout(2))
        backgroundPanel.addComponent(footerPanel, BorderLayout.Location.BOTTOM)

        footerLabel = Label("Hello from lanterna-sqlite-starter!")
        footerPanel.addComponent(footerLabel, GridLayout.createLayoutData(
            GridLayout.Alignment.BEGINNING, GridLayout
                .Alignment.BEGINNING))
        footerPanel.addComponent(ThrobberService.throbber, GridLayout.createLayoutData(
            GridLayout.Alignment.BEGINNING, GridLayout
                .Alignment.BEGINNING))

        windowManager = DefaultWindowManager()
        textGUI = MultiWindowTextGUI(screen, windowManager, backgroundPanel)

        (screen.terminal as? SwingTerminalFrame)?.apply {
            size = Dimension(SWING_WIDTH, SWING_HEIGHT)
            Logger.loggingEnabled = true // for swing panel, which is separate from the console, we can enable logs
        }

        BrowserHelper.waitingOp(textGUI, "Updating database...") {
            if (Db.empty)
                Db.populateTestData()
            Thread.sleep(2000) // TODO remove me, this is just a showcase for the throbber
        }

//        textGUI.addWindowAndWait(PhrasesBrowserWindow().window)

        ExecutorService.done()
        screen.stopScreen()

    }

}
