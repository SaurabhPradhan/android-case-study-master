package com.target.targetcasestudy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = Products.TABLE_NAME)
data class Products(
    @PrimaryKey
    @ColumnInfo(name = "dealsId")
    @SerializedName("id")
    var dealsId: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var aisle: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("regular_price")
    var regularPrice: RegularPrice? = null,
    @SerializedName("sale_price")
    var salePrice: SalePrice? = null
) {
    companion object {
        const val TABLE_NAME = "deals_table"
    }

}