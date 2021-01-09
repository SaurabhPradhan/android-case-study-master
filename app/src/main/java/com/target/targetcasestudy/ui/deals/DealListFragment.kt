package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.FragmentDealListBinding
import com.target.targetcasestudy.model.State
import com.target.targetcasestudy.ui.activity.MainActivity
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.ui.deals.adapter.DealItemAdapter
import com.target.targetcasestudy.viewmodel.DealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealListFragment : BaseFragment<DealsViewModel>() {

    private lateinit var binding: FragmentDealListBinding

    override val mViewModel: DealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDealListBinding.inflate(inflater, container, false)
        return binding.root
    }


    private var mAdapter = DealItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setHasOptionsMenu(true)
    }

    private fun initObserver() {
        mViewModel.getAllDeals()
        // The onChanged() method fires when the observed data changes and the fragment is
        // in the foreground.
        mViewModel.postsLiveData.observe(requireActivity(), Observer { state ->
            when (state) {
                is State.Loading -> {
                    (activity as MainActivity).showProgress()
                }
                is State.Success -> {
                    (activity as MainActivity).hideProgress()
                    if (state.data.products.isNotEmpty()) {
                        binding.recyclerView.adapter = mAdapter
                        mAdapter.setData(state.data.products.toMutableList())
                    }
                }
                is State.Error -> {
                    (activity as MainActivity).hideProgress()
                }
            }
        })

        mAdapter.productLiveData.observe(requireActivity(), Observer {
            if (it != null)
                actionListener?.onAction(ACTION_START_DEAL_ITEM_VIEW, it)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.creditCard).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    companion object {
        const val ACTION_START_DEAL_ITEM_VIEW = "action_deals_item_view"
    }

}
