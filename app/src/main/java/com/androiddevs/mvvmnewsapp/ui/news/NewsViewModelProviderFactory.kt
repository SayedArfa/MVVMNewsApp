package com.androiddevs.mvvmnewsapp.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.data.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsViewModelProviderFactory(
     val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}