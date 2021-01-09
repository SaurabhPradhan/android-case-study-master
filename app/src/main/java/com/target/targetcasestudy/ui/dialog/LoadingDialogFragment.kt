package com.target.targetcasestudy.ui.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?) = LoadingDialog(requireActivity())

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().apply {
            add(this@LoadingDialogFragment, tag)
            commitAllowingStateLoss()
        }
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }
}