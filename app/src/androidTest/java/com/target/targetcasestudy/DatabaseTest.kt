package com.target.targetcasestudy

import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.target.targetcasestudy.data.local.DealsDataBase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    private lateinit var dealsDatabase: DealsDataBase

    @Before
    fun initDb() {
        dealsDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            DealsDataBase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        dealsDatabase.close()
    }

    @Test
    fun insertDealsData() {
        runBlocking {
            val cachedProducts = DealsFactory.makeProductEntityList(1)
            dealsDatabase.dealsDao().insertDeals(cachedProducts)
            assert(dealsDatabase.dealsDao().getAllDeals().isNotEmpty())
        }
    }

    @Test
    fun getDealsDataById() {
        runBlocking {
            val cachedProducts = DealsFactory.makeProductEntityList(1)
            dealsDatabase.dealsDao().insertDeals(cachedProducts)

        }
        dealsDatabase.dealsDao().getDetailedDealInfo(0).asLiveData().value?.title?.isNotEmpty()?.let {
            assert(
                it
            )
        }
    }
}