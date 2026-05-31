package com.mashakulabukhova.expensesharingsystem.utils

import android.util.Log
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import retrofit2.Response

class BaseApiResponse {

    suspend fun <T, R> safeApiCall(
        api: suspend () -> Response<T>,
        mapper: (T) -> R
    ): NetworkResult<R> {
        return try {
            val response = api()
            Log.d("BaseApiResponse", "Response: $response")
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    NetworkResult.Success(mapper(body))
                } ?: NetworkResult.Error(message = "Ошибка загрузки")
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: Exception) {
            Log.d("BaseApiResponse", "Exception: $e")
            NetworkResult.Error(message = e.message ?: "Ошибка загрузки")
        }
    }

    suspend fun <T, R> safeApiCallExtract(
        api: suspend () -> Response<T>,
        extractor: (T) -> R?
    ): NetworkResult<R> {
        return try {
            val response = api()
            Log.d("BaseApiResponse", "Response: $response")
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { wrapper ->
                    extractor(wrapper)?.let { extractedData ->
                        NetworkResult.Success(extractedData)
                    } ?: NetworkResult.Error(message = "Ошибка загрузки")
                } ?: NetworkResult.Error(message = "Ошибка загрузки")
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: Exception) {
            Log.d("BaseApiResponse", "Exception: $e")
            NetworkResult.Error(message = e.message ?: "Ошибка загрузки")
        }
    }

    suspend fun safeApiCallNoBody(api: suspend() -> Response<Void>): NetworkResult<Unit> {
        return try {
            val response = api()
            Log.d("BaseApiResponse", "Response: $response")
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    message = response.message()
                )
            }
        } catch (e: Exception) {
            Log.d("BaseApiResponse", "Exception: $e")
            NetworkResult.Error(message = e.message ?: "Unknown error")
        }
    }
}