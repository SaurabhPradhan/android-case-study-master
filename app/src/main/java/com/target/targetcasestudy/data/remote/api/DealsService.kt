package com.target.targetcasestudy.data.remote.api

import com.target.targetcasestudy.model.Deals
import retrofit2.Response
import retrofit2.http.GET

interface DealsService {
    @GET("deals")
    suspend fun getDeals(): Response<Deals>
}