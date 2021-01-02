package com.androiddevs.mvvmnewsapp.data.remote

import com.androiddevs.mvvmnewsapp.domain.Resource
import com.androiddevs.mvvmnewsapp.util.BaseNetworkHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object SafeApiCall {
    suspend operator fun <T> invoke(
        networkHelper: BaseNetworkHelper,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(coroutineDispatcher) {
            if (!networkHelper.hasInternetConnection()) {
                Resource.Error("IOException", null)
            } else {
                try {
                    val response = apiCall.invoke()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Resource.Success(it)
                        } ?: Resource.Error(response.message(), null)
                    } else
                        Resource.Error(response.message(), null)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            Resource.Error("IOException", null)
                        }
                        is HttpException -> {
                            Resource.Error("HttpException", null)
                        }
                        else -> {
                            Resource.Error("UnknownException", null)
                        }
                    }
                }
            }

        }
    }
}