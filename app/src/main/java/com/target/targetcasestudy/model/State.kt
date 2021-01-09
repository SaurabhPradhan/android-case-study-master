package com.target.targetcasestudy.model

/**
 * This class will tell us the status whether data is fetched successfully or failed.
 */
sealed class State<T> {

    class Loading<T> : State<T>()

    data class Error<T>(val message: String) : State<T>()

    data class Success<T>(val data: T) : State<T>()

    companion object {
        /**
         * Return [State.Loading] instance.
         */
        fun <T> loading() = Loading<T>()

        /**
         * Return [State.success] instance.
         * @param data Data to emit with
         */
        fun <T> success(data: T): State<T> = Success(data)

        /**
         * Return [State.Error] instance.
         * @param message Description of failure
         */
        fun <T> error(message: String) = Error<T>(message)
    }
}