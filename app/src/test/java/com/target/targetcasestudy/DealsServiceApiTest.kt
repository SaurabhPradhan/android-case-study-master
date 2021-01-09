package com.target.targetcasestudy

import com.google.gson.GsonBuilder
import com.target.targetcasestudy.data.remote.api.DealsService
import com.target.targetcasestudy.util.FileReader
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class DealsServiceApiTest {
    private var mockWebServer = MockWebServer()
    private lateinit var dealsApi: DealsService

    @Before
    fun setup() {
        mockWebServer.start()
        dealsApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(DealsService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Given we have a valid request, should be done to correct url`() {
        runBlocking {
            val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(FileReader("deals.json").content)
            mockWebServer.enqueue(response)
            dealsApi.getDeals()
            val request = mockWebServer.takeRequest()
            Assert.assertTrue(request.path.equals("/deals"))
        }
    }

    @Test
    fun `request failed`() {
        runBlocking {
            val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody(FileReader("deals_not_found.json").content)
            mockWebServer.enqueue(response)
            Assert.assertTrue(dealsApi.getDeals().code() == HttpURLConnection.HTTP_NOT_FOUND)
        }
    }

    @Test
    fun `request with data`() {
        runBlocking {
            val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(FileReader("deals.json").content)
            mockWebServer.enqueue(response)
            dealsApi.getDeals().body()?.products?.isNotEmpty()?.let { Assert.assertTrue(it) }
        }
    }
}
