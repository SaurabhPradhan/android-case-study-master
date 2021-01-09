package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.State
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.ui.deals.adapter.DealItemAdapter
import com.target.targetcasestudy.viewmodel.DealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deal_list.*

@AndroidEntryPoint
class DealListFragment : BaseFragment<DealsViewModel>() {

    override fun layoutResource() = R.layout.fragment_deal_list

    override val mViewModel: DealsViewModel by viewModels()

    private var mAdapter = DealItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        mViewModel.getAllDeals()
        // Add an observer on the LiveData returned by get recipe.
        // The onChanged() method fires when the observed data changes and the fragment is
        // in the foreground.
        mViewModel.postsLiveData.observe(requireActivity(), Observer { state ->
            when (state) {
                is State.Loading -> {
                 }
                is State.Success -> {
                    recyclerView.visibility = View.VISIBLE
                    if (state.data.products.isNotEmpty()) {
                        recyclerView.adapter = mAdapter
                        mAdapter.submitList(state.data.products.toMutableList())

                    }
                }
                is State.Error -> {
                }
            }
        })
    }
}
