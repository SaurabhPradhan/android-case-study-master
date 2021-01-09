package com.target.targetcasestudy.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.target.targetcasestudy.R

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    abstract fun layoutResource(): Int

    protected abstract val mViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutResource(), container, false)

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