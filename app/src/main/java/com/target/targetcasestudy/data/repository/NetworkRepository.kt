package com.target.targetcasestudy.data.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.target.targetcasestudy.model.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A repository which provides data from local database as well as remote end point.
 *
 * [RESULT] represents the type for database.
 * [REQUEST] represents the type for network.
 */
abstract class NetworkRepository<RESULT, REQUEST> {

    fun asFlow() = flow<State<RESULT>> {
        emit(State.loading())
        emit(State.success(fetchFromLocal().first()))
        val apiResponse = fetchFromRemote()
        val remoteDeals = apiResponse.body()
        if (apiResponse.isSuccessful && remoteDeals != null) {
            saveRemoteData(remoteDeals)
        } else {
            emit(State.error(apiResponse.message()))
        }
        emitAll(fetchFromLocal().map { State.success(it) })
    }.catch { e ->
        emit(State.error(e.message.toString()))
    }

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    @WorkerThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>

    @MainThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

}