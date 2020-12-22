package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import com.androiddevs.mvvmnewsapp.extensions.hasInternetConnection

class NetworkHelper(private val context: Context) : BaseNetworkHelper {
    @Override
    override fun hasInternetConnection() = context.hasInternetConnection()
}

interface BaseNetworkHelper {
    fun hasInternetConnection(): Boolean
}