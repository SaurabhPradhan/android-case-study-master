package com.target.targetcasestudy

import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.model.RegularPrice
import com.target.targetcasestudy.model.SalePrice

class DealsFactory {
    companion object Factory {

        private fun makeProductEntity(): Products {
            return Products(
                0, "Tiered Lace TanK", "", "", "https://picsum.photos/id/0/300/300",
                RegularPrice(4025, "$", "$40.25"), SalePrice(734, "$", "$7.34")
            )
        }

        fun makeProductEntityList(count: Int): List<Products> {
            val productEntities = mutableListOf<Products>()
            repeat(count) {
                productEntities.add(makeProductEntity())
            }
            return productEntities
        }
    }
}