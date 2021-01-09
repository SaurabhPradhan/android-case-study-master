package com.target.targetcasestudy.data.repository

import androidx.annotation.MainThread
import com.target.targetcasestudy.data.local.dao.DealsDao
import com.target.targetcasestudy.data.remote.api.DealsService
import com.target.targetcasestudy.model.Deals
import com.target.targetcasestudy.model.Products
import com.target.targetcasestudy.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DealsRepository @Inject constructor(
    private val service: DealsService,
    private val dao: DealsDao
) {

    fun getAllDeals(): Flow<State<Deals>> {
        return object : NetworkRepository<Deals, Deals>() {
            override suspend fun saveRemoteData(response: Deals) =
                dao.insertDeals(response.products)

            override fun fetchFromLocal(): Flow<Deals> =
                flow { emit(Deals(dao.getAllDeals())) }

            override suspend fun fetchFromRemote(): Response<Deals> = service.getDeals()

        }.asFlow()
    }

    /**
     * Retrieves a recipe with specified [dealsId].
     * @param dealsId Unique id of a [DealItem].
     * @return [DealItem] data fetched from the database.
     */
    @MainThread
    fun getDetailedRecipeData(dealsId: String): Flow<Products> =
        dao.getDetailedDealInfo(dealsId)
}
