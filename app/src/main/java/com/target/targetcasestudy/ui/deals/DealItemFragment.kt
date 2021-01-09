package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.FragmentDealItemBinding
import com.target.targetcasestudy.ui.activity.MainActivity
import com.target.targetcasestudy.ui.activity.MainActivity.Companion.KEY_DEAL_DATA
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.ui.deals.DealListFragment.Companion.ACTION_START_DEAL_ITEM_VIEW
import com.target.targetcasestudy.util.GlideParams
import com.target.targetcasestudy.viewmodel.DealsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealItemFragment : BaseFragment<DealsViewModel>() {

    private lateinit var mItemBinding: FragmentDealItemBinding

    override val mViewModel: DealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mItemBinding = FragmentDealItemBinding.inflate(inflater, container, false)
        return mItemBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initObserver()
    }

    private fun initObserver() {
        // The onChanged() method fires when the observed data changes and the fragment is
        // in the foreground.
        arguments?.getInt(KEY_DEAL_DATA)?.let {
            mViewModel.getDetailedDealsData(it).observe(requireActivity(), Observer { product ->
                mItemBinding.salePrice.text =
                    if (product.salePrice != null) product.salePrice?.display_string else ""
                mItemBinding.regularPrice.text = product.regularPrice?.display_string
                mItemBinding.detailImageView.setImageUrl(
                    product.imageUrl.toString(),
                    GlideParams(errorImage = R.drawable.glide_error)
                )
                mItemBinding.title.text = product.title
                mItemBinding.description.text = product.description
            })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.creditCard).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    companion object {
        const val ACTION_BACK = "action_back"
    }
}
