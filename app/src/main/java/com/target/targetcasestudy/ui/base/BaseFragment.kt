package com.target.targetcasestudy.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.target.targetcasestudy.R

/**
 * Abstract fragment which binds [ViewModel] [VM]
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