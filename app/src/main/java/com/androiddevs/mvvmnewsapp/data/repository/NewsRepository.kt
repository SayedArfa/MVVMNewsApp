package com.androiddevs.mvvmnewsapp.data.repository

import com.androiddevs.mvvmnewsapp.data.local.datasource.NewsLocalDataSource
import com.androiddevs.mvvmnewsapp.data.local.mapper.ArticleMapper
import com.androiddevs.mvvmnewsapp.data.remote.SafeApiCall
import com.androiddevs.mvvmnewsapp.data.remote.datasource.NewsRemoteDataSource
import com.androiddevs.mvvmnewsapp.domain.Resource
import com.androiddevs.mvvmnewsapp.domain.models.Article
import com.androiddevs.mvvmnewsapp.domain.models.NewsResponse
import com.androiddevs.mvvmnewsapp.domain.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.BaseNetworkHelper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NewsRepository @Inject constructor(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val networkHelper: BaseNetworkHelper
) : NewsRepository {
    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Resource<NewsResponse> {

        return SafeApiCall(networkHelper) {
            newsRemoteDataSource.getBreakingNews(countryCode, pageNumber)
        }
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        return SafeApiCall(networkHelper) {
            newsRemoteDataSource.searchForNews(searchQuery, pageNumber)
        }
    }

    override suspend fun upsert(article: Article) =
        newsLocalDataSource.upsert(ArticleMapper().mapToEntity(article))

    override suspend fun getSavedNews(): List<Article> =
        newsLocalDataSource.getSavedNews().map {
            ArticleMapper().mapFromEntity(it)
        }

    override suspend fun deleteArticle(article: Article) =
        newsLocalDataSource.deleteArticle(ArticleMapper().mapToEntity(article))


}