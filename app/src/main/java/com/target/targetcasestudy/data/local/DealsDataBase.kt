package com.target.targetcasestudy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.local.dao.DealsDao
import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.util.RegularPriceConverter
import com.target.targetcasestudy.util.SalesPriceConverter

/**
 * This is the class will create the deals database.
 */
@Database(entities = [Products::class], version = 1, exportSchema = false)
@TypeConverters(RegularPriceConverter::class, SalesPriceConverter::class)
abstract class DealsDataBase : RoomDatabase() {

    abstract fun dealsDao(): DealsDao

    companion object {
        private lateinit var instance: DealsDataBase

        @Synchronized
        fun getInstance(context: Context): DealsDataBase {
            if (!this::instance.isInitialized) {
                instance = Room.databaseBuilder(
                    context.applicationContext, DealsDataBase::class.java,
                    context.getString(R.string.my_deals_db)
                ).build()
            }
            return instance
        }
    }
}