package com.androiddevs.mvvmnewsapp.ui.news

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

import com.androiddevs.mvvmnewsapp.data.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.domain.Resource
import com.androiddevs.mvvmnewsapp.domain.models.Article
import com.androiddevs.mvvmnewsapp.domain.models.NewsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null


    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        handleBreakingNewsResponse(response)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        handleSearchNewsResponse(response)
    }

    private fun handleBreakingNewsResponse(response: Resource<NewsResponse>) {
        if (response is Resource.Success) {
            response.data?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                breakingNews.postValue(Resource.Success(breakingNewsResponse ?: resultResponse))
            }
        } else
            breakingNews.postValue(response)
    }


    private fun handleSearchNewsResponse(response: Resource<NewsResponse>) {
        if (response is Resource.Success) {
            response.data?.let { resultResponse ->
                if (searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                searchNews.postValue(Resource.Success(searchNewsResponse ?: resultResponse))
            }
        } else
            searchNews.postValue(response)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
        getSavedNews()
    }

    private val _savedNews = MutableLiveData<List<Article>>()
    val savedNews: LiveData<List<Article>> = _savedNews
    fun getSavedNews() {
        viewModelScope.launch {
            _savedNews.postValue(newsRepository.getSavedNews())
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
        getSavedNews()
    }
}












