package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.target.targetcasestudy.R
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.viewmodel.DealsViewModel


class DealItemFragment : BaseFragment<DealsViewModel>() {

    override fun layoutResource() = R.layout.fragment_deal_item

    override val mViewModel: DealsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
