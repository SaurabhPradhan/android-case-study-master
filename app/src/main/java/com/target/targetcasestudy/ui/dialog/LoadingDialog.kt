package com.target.targetcasestudy.ui.dialog

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.ViewGroup
import android.view.Window
import com.target.targetcasestudy.R

class LoadingDialog(context: Context) : AlertDialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            attributes.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
                horizontalMargin = 0f
                verticalMargin = 0f
            }
            setBackgroundDrawable(null)
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        setContentView(R.layout.dialog_loading)
    }
}