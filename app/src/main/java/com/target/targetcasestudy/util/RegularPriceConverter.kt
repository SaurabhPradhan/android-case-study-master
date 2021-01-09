package com.target.targetcasestudy.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.target.targetcasestudy.model.RegularPrice

object RegularPriceConverter {

    @TypeConverter
    @JvmStatic
    fun regularPriceToString(regularPrice: RegularPrice): String = Gson().toJson(regularPrice)

    @TypeConverter
    @JvmStatic
    fun stringToRegularPrice(string: String): RegularPrice =
        Gson().fromJson(string, RegularPrice::class.java)

}