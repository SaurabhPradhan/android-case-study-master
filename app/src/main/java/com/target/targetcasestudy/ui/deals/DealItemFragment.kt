package com.target.targetcasestudy.ui.deals

import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.FragmentDealItemBinding
import com.target.targetcasestudy.ui.activity.MainActivity
import com.target.targetcasestudy.ui.activity.MainActivity.Companion.KEY_DEAL_DATA
import com.target.targetcasestudy.ui.base.BaseFragment
import com.target.targetcasestudy.util.GlideParams
import com.target.targetcasestudy.util.gone
import com.target.targetcasestudy.viewmodel.DealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


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
        (activity as MainActivity).back.setOnClickListener {
            actionListener?.onAction(ACTION_BACK)
        }
    }

    private fun initObserver() {
        // The onChanged() method fires when the observed data changes and the fragment is
        // in the foreground.
        arguments?.getInt(KEY_DEAL_DATA)?.let {
            mViewModel.getDetailedDealsData(it).observe(requireActivity(), Observer { product ->
                val isSalePriceEmpty = product.salePrice?.display_string?.isEmpty()
                if (isSalePriceEmpty == true) {
                    mItemBinding.regularPrice.gone()
                    mItemBinding.salePrice.text = product.regularPrice?.display_string
                } else {
                    mItemBinding.salePrice.text = product.salePrice?.display_string
                }
                mItemBinding.regularPrice.apply {
                    setText(
                        getString(
                            R.string.regular_price_text,
                            product.regularPrice?.display_string
                        ),
                        TextView.BufferType.SPANNABLE
                    )
                    (text as Spannable).setSpan(
                        StrikethroughSpan(),
                        START_STRIKE_OUT_FROM,
                        length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
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
        private const val START_STRIKE_OUT_FROM = 5
    }
}
