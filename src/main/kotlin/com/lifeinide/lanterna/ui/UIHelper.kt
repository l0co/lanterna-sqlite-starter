package com.lifeinide.lanterna.ui

import com.googlecode.lanterna.gui2.WindowBasedTextGUI
import com.googlecode.lanterna.gui2.dialogs.WaitingDialog
import com.lifeinide.lanterna.service.ExecutorService
import com.lifeinide.lanterna.service.Logger
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Additional helper for UI.
 *
 * @author Lukasz Frankowski
 */
object UIHelper {

    /**
     * Displays waiting window and executed background operation using the `ExecutorService`.
     */
    fun waitingOp(gui: WindowBasedTextGUI, msg: String, op: () -> Unit) {
        WaitingDialog.createDialog("Please wait", msg).let {
            Logger.log("Adding waiting window: $msg")
            gui.addWindow(it)
            val future = ExecutorService.execute {
                op()
            }

            while (true) {
                try {
                    future.get(10, TimeUnit.MILLISECONDS)
                    Logger.log("Removing waiting window: $msg")
                    it.close()
                    break
                } catch (e: TimeoutException) {
                    it.textGUI.guiThread.processEventsAndUpdate()
                }
            }

        }
    }

}
