package com.androiddevs.mvvmnewsapp.data.remote.datasource

import com.androiddevs.mvvmnewsapp.data.remote.api.NewsAPI
import com.androiddevs.mvvmnewsapp.domain.models.NewsResponse
import retrofit2.Response

class NewsRemoteDataSource(val api: NewsAPI) {

    suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return api.getBreakingNews(countryCode, pageNumber)
    }


    suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int
    ): Response<NewsResponse> {
        return api.searchForNews(searchQuery, pageNumber)
    }
}