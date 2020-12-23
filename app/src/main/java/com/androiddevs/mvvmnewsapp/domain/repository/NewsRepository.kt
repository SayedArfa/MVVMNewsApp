package com.androiddevs.mvvmnewsapp.domain.repository

import com.androiddevs.mvvmnewsapp.domain.Resource
import com.androiddevs.mvvmnewsapp.domain.models.Article
import com.androiddevs.mvvmnewsapp.domain.models.NewsResponse

interface NewsRepository {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Resource<NewsResponse>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse>

    suspend fun upsert(article: Article): Long

    suspend fun getSavedNews(): List<Article>

    suspend fun deleteArticle(article: Article)
}