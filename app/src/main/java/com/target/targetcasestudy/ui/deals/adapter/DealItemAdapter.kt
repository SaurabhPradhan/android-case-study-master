package com.target.targetcasestudy.ui.deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.util.GlideParams
import com.target.targetcasestudy.util.NetworkImageView
import kotlinx.android.synthetic.main.deal_list_item.view.*


class DealItemAdapter : ListAdapter<Products, DealItemAdapter.DealItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DealItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.deal_list_item, parent, false)
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
        }
    }

    inner class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: NetworkImageView = itemView.productImageView
        val productTitle: TextView = itemView.itemTitle
        val productPrice: TextView = itemView.itemPrice
        val productAisleType: TextView = itemView.aisleType
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean =
                oldItem.dealsId == newItem.dealsId

            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean =
                oldItem == newItem
        }
    }
}

