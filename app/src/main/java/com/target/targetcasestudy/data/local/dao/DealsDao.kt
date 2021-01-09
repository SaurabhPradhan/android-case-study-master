package com.target.targetcasestudy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.model.Products.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface DealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeals(deal: List<Products>)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllDeals(): List<Products>

    @Query("SELECT * FROM $TABLE_NAME WHERE dealsId=:id")
    fun getDetailedDealInfo(id: Int): Flow<Products>

}