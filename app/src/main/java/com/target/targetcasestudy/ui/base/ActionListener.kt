package com.target.targetcasestudy.ui.base

import java.lang.Exception

interface ActionListener {
    /** Called by a fragment to request the Activity perform an action
     * @param action should be unique by fragment and action.
     * Format is ClassName::class.java.simpleName +".action"
     * where ".action" is descriptive of the action
     * @param data optional data to be passed back to activity
     */
    fun onAction(action: String, data: Any? = null)

    /** custom exception to handle exception related to ActionListener.
     * @param message is the message to be passed while reading exception
     */
    class ActionListenerException(override var message: String) : Exception(message)
}
