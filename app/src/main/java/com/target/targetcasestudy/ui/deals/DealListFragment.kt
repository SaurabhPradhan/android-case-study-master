package com.target.targetcasestudy.ui.deals

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.FragmentDealListBinding
import com.target.targetcasestudy.model.State
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.ui.deals.adapter.DealItemAdapter
import com.target.targetcasestudy.util.NetworkUtils
import com.target.targetcasestudy.util.gone
import com.target.targetcasestudy.util.show
import com.target.targetcasestudy.viewmodel.DealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealListFragment : BaseFragment<DealsViewModel>() {

    private lateinit var binding: FragmentDealListBinding

    private var mAdapter = DealItemAdapter()

    override val mViewModel: DealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDealListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = mAdapter
        initObserver()
        setHasOptionsMenu(true)
    }

    private fun initObserver() {
        // The onChanged() method fires when the observed data changes and the fragment is
        // in the foreground.
        mViewModel.postsLiveData.observe(requireActivity(), Observer { state ->
            when (state) {
                is State.Loading -> {
                    showStatusText(getString(R.string.text_loading), R.color.colorStatusConnected)
                }
                is State.Success -> {
                    if (state.data.products.isNotEmpty()) {
                        mAdapter.setData(state.data.products.toMutableList())
                        binding.textStatus.gone()
                    }
                }
                is State.Error -> {
                    showGenericStatus(getString(R.string.text_error_loading))
                    if (mAdapter.itemCount == 0) {
                        mViewModel.getAllDeals()
                    }
                }
            }
        })

        mAdapter.productLiveData.observe(requireActivity(), Observer {
            if (it != null)
                actionListener?.onAction(ACTION_START_DEAL_ITEM_VIEW, it)
        })

        NetworkUtils.getNetworkLiveData(requireContext().applicationContext)
            .observe(requireActivity(), Observer { isConnected ->
                if (!isConnected) {
                    showStatusText(
                        getString(R.string.text_no_connectivity),
                        R.color.colorStatusNotConnected
                    )
                } else {
                    showGenericStatus(getString(R.string.text_connectivity))
                }
            })
    }

    private fun showStatusText(msg: String, color: Int) {
        binding.textStatus.apply {
            text = msg
            show()
            setBackgroundColor(getColor(context, color))
        }
    }

    private fun showGenericStatus(msg: String) {
        binding.textStatus.apply {
            setBackgroundColor(getColor(context, R.color.colorStatusConnected))
            text = msg
            animate().alpha(1f).setStartDelay(ANIMATION_DURATION)
                .setDuration(ANIMATION_DURATION)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        gone()
                        if (mViewModel.postsLiveData.value is State.Error || mAdapter.itemCount == 0) {
                            mViewModel.getAllDeals()
                        }
                    }
                })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.creditCard).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    companion object {
        const val ACTION_START_DEAL_ITEM_VIEW = "action_deals_item_view"
        const val ANIMATION_DURATION = 300.toLong()
    }
}
