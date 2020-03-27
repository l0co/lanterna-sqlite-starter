package com.lifeinide.lanterna.ui

import com.googlecode.lanterna.gui2.*
import com.lifeinide.lanterna.db.service.MyEntityService
import com.lifeinide.lanterna.service.Logger
import com.lifeinide.lanterna.ui.base.WindowComponent

/**
 * @author Lukasz Frankowski
 */
class MainWindow: WindowComponent() {

    override fun buildWindow(): Window {
        return BasicWindow("MyEntity Browser").apply {
            // setHints(mutableSetOf(Window.Hint.EXPANDED))
            setCloseWindowWithEscape(true)
        }
    }

    override val component: Component by lazy {
        val contentPanel = Panel(BorderLayout())

        val actionList = ActionListBox()
        MyEntityService.findAll().forEach {
            actionList.addItem("id=${it.id} str=${it.str} int=${it.int} dbl=${it.dbl} bool=${it.bool}") {
                Logger.log("ENTER hit on ${it.id}")
            }
        }

        contentPanel.addComponent(actionList, BorderLayout.Location.CENTER)
        focusComponent = actionList

        contentPanel
    }
    
}
