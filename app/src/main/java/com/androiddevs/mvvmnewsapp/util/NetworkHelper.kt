package com.androiddevs.mvvmnewsapp.util

import android.content.Context
import com.androiddevs.mvvmnewsapp.extensions.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) :
    BaseNetworkHelper {
    @Override
    override fun hasInternetConnection() = context.hasInternetConnection()
}

interface BaseNetworkHelper {
    fun hasInternetConnection(): Boolean
}