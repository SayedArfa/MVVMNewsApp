package com.androiddevs.mvvmnewsapp.data.repository

import com.androiddevs.mvvmnewsapp.data.local.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.local.mapper.ArticleMapper
import com.androiddevs.mvvmnewsapp.data.remote.api.NewsAPI
import com.androiddevs.mvvmnewsapp.data.remote.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.domain.Resource
import com.androiddevs.mvvmnewsapp.domain.models.Article
import com.androiddevs.mvvmnewsapp.domain.models.NewsResponse
import com.androiddevs.mvvmnewsapp.domain.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.BaseNetworkHelper
import java.io.IOException

class NewsRepository(
    val db: ArticleDatabase, val newsApi: NewsAPI, val networkHelper: BaseNetworkHelper
) : NewsRepository {
    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Resource<NewsResponse> {
        try {
            if (networkHelper.hasInternetConnection()) {
                val response = newsApi.getBreakingNews(countryCode, pageNumber)
                if (response.isSuccessful) {
                    response.body()?.let {
                        return Resource.Success(it)
                    }
                }
                return Resource.Error(response.message())
            } else {
                return Resource.Error("No internet connection")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> return Resource.Error("Network Failure")
                else -> return Resource.Error("Conversion Error")
            }
        }
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        try {
            if (networkHelper.hasInternetConnection()) {
                val response = RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
                if (response.isSuccessful) {
                    response.body()?.let {
                        return Resource.Success(it)
                    }
                }
                return Resource.Error(response.message())


            } else {
                return Resource.Error("No internet connection")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> return Resource.Error("Network Failure")
                else -> return Resource.Error("Conversion Error")
            }
        }

    }

    override suspend fun upsert(article: Article) =
        db.getArticleDao().upsert(ArticleMapper().mapToEntity(article))

    override suspend fun getSavedNews(): List<Article> =
        db.getArticleDao().getAllArticles().map {
            ArticleMapper().mapFromEntity(it)
        }

    override suspend fun deleteArticle(article: Article) =
        db.getArticleDao().deleteArticle(ArticleMapper().mapToEntity(article))


}