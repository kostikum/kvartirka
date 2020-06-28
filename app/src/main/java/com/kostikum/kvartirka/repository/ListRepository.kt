package com.kostikum.kvartirka.repository

import com.kostikum.kvartirka.entity.CountryResponse
import com.kostikum.kvartirka.entity.FlatsResponse
import com.kostikum.kvartirka.network.NetProvider
import com.kostikum.kvartirka.util.Either
import com.kostikum.kvartirka.util.Either.Left
import com.kostikum.kvartirka.util.Either.Right
import com.kostikum.kvartirka.util.Failure
import com.kostikum.kvartirka.util.Failure.NetworkConnection
import com.kostikum.kvartirka.util.Failure.ServerError
import com.kostikum.kvartirka.util.NetworkHandler
import retrofit2.Call

interface Repository {
    fun countries(): Either<Failure, CountryResponse>
    fun flats(cityId: Int): Either<Failure, FlatsResponse>

    class Network(private val networkHandler: NetworkHandler) : Repository {

        override fun countries(): Either<Failure, CountryResponse> {
            return when (networkHandler.isConnected) {
                true -> request(NetProvider.service.getCountries(), CountryResponse((emptyList()))) { it }
                false, null -> Left(NetworkConnection)
            }
        }

        override fun flats(cityId: Int): Either<Failure, FlatsResponse> {
            return when (networkHandler.isConnected) {
                true -> request(NetProvider.service.getFlatsByCity(cityId), FlatsResponse(emptyList())) { it }
                false, null -> Left(NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, default: T, transform: (T) -> R): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Right(transform((response.body() ?: default)))
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                Left(ServerError)
            }
        }
    }
}
