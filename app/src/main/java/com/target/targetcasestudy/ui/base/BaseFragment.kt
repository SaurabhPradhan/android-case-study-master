package com.target.targetcasestudy.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.target.targetcasestudy.R

/**
 * Abstract fragment which binds [ViewModel] [VM] and activity
 */
abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected abstract val mViewModel: VM

    var actionListener: ActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionListener) {
            actionListener = context
        } else {
            throw ActionListener.ActionListenerException(getString(R.string.action_listener_exception))
        }
    }

    override fun onDetach() {
        actionListener = null
        super.onDetach()
    }
}