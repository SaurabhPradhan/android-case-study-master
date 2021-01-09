package com.target.targetcasestudy.model

import com.google.gson.annotations.SerializedName

data class RegularPrice(
    @SerializedName("amount_in_cents")
    val regularPrice: Int? = null,
    val currency_symbol: String? = null,
    val display_string: String? = null
)