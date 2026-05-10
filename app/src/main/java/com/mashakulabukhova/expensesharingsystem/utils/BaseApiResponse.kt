package com.mashakulabukhova.expensesharingsystem.utils

import android.util.Log
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
                } ?: NetworkResult.Error(message = "Body is empty")
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
                    } ?: NetworkResult.Error(message = "Failed to extract data from wrapper")
                } ?: NetworkResult.Error(message = "Body is empty")
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
//
//    suspend fun safeApiCallNoBody(api: suspend() -> Response<Void>): NetworkResult<Unit> {
//        return try {
//            val response = api()
//            if (response.isSuccessful) {
//                NetworkResult.Success(Unit)
//            } else {
//                errorMessage("${response.code()} ${response.message()}")
//            }
//        } catch (e: Exception) {
//            errorMessage(e.message.toString())
//        }
//    }
//
//    private fun <T> errorMessage(e: String): NetworkResult.Error<T> =
//        NetworkResult.Error(message = "Api call failed: ${e}")
}