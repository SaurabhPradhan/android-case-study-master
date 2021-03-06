package com.target.targetcasestudy.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.target.targetcasestudy.model.SalePrice

/**
 * This converter class by room data base so that room can understand how to store or retrieve the SalePrice object
 */
object SalesPriceConverter {

    @TypeConverter
    @JvmStatic
    fun salePriceToString(salePrice: SalePrice?): String =
        Gson().toJson(salePrice ?: SalePrice(-1, "", ""))

    @TypeConverter
    @JvmStatic
    fun stringToSalePrice(value: String): SalePrice = Gson().fromJson(value, SalePrice::class.java)
}