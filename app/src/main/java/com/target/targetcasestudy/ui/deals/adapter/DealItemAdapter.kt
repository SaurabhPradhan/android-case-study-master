package com.target.targetcasestudy.ui.deals.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.databinding.DealListItemBinding
import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.ui.base.ActionListener
import com.target.targetcasestudy.util.GlideParams
import com.target.targetcasestudy.util.NetworkImageView

class DealItemAdapter : ListAdapter<Products, DealItemAdapter.DealItemViewHolder>(DIFF_CALLBACK),
    ActionListener {

    var productLiveData = MutableLiveData<Int?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DealItemViewHolder(
        DealListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.apply {
            productTitle.text = item.title
            val itemPrice = item.salePrice?.display_string ?: ""
            if (itemPrice.isNotEmpty()) {
                productPrice.text = itemPrice
            } else {
                productPrice.text = item.regularPrice?.display_string
            }
            productAisleType.text = item.aisle
            item.imageUrl?.let {
                productImage.setImageUrl(
                    it,
                    GlideParams(errorImage = R.drawable.glide_error)
                )
            }
            itemView.setOnClickListener {
                onAction(ACTION_ITEM_SELECTED, item.dealsId)
            }
        }
    }

    fun setData(products: List<Products>) {
        productLiveData.value = null
        submitList(products.toMutableList())
    }

    override fun onAction(action: String, data: Any?) {
        if (action == ACTION_ITEM_SELECTED)
            productLiveData.value = data as Int
    }

    inner class DealItemViewHolder(itemView: DealListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val productImage: NetworkImageView = itemView.productImageView
        val productTitle: TextView = itemView.itemTitle
        val productPrice: TextView = itemView.itemPrice
        val productAisleType: TextView = itemView.aisleType
    }

    companion object {
        private const val ACTION_ITEM_SELECTED = "action_item_selected"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean =
                oldItem.dealsId == newItem.dealsId

            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean =
                oldItem == newItem
        }
    }
}

