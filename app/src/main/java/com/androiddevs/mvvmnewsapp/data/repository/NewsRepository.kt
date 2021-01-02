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

class NewsRepository(
    val newsLocalDataSource: NewsLocalDataSource,
    val newsRemoteDataSource: NewsRemoteDataSource,
    val networkHelper: BaseNetworkHelper
) : NewsRepository {
    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Resource<NewsResponse> {

        return SafeApiCall(networkHelper) {
            newsRemoteDataSource.getBreakingNews(countryCode, pageNumber)
        }
        /*try {
            if (networkHelper.hasInternetConnection()) {
                val response = newsRemoteDataSource.getBreakingNews(countryCode, pageNumber)
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
        }*/
    }

    override suspend fun searchNews(searchQuery: String, pageNumber: Int): Resource<NewsResponse> {
        return SafeApiCall(networkHelper) {
            newsRemoteDataSource.searchForNews(searchQuery, pageNumber)
        }
        /*  try {
              if (networkHelper.hasInternetConnection()) {
                  val response = newsRemoteDataSource.searchForNews(searchQuery, pageNumber)
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
          }*/

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