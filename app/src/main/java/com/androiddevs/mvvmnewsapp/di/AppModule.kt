package com.androiddevs.mvvmnewsapp.di

import android.content.Context
import com.androiddevs.mvvmnewsapp.data.local.datasource.NewsLocalDataSource
import com.androiddevs.mvvmnewsapp.data.local.db.ArticleDao
import com.androiddevs.mvvmnewsapp.data.local.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.remote.api.NewsAPI
import com.androiddevs.mvvmnewsapp.data.remote.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.data.remote.datasource.NewsRemoteDataSource
import com.androiddevs.mvvmnewsapp.util.BaseNetworkHelper
import com.androiddevs.mvvmnewsapp.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return ArticleDatabase.invoke(context)
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDatabase): ArticleDao {
        return db.getArticleDao()
    }

    @Singleton
    @Provides
    fun provideNewsApi(): NewsAPI {
        return RetrofitInstance.api
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context):BaseNetworkHelper{
        return NetworkHelper(context)
    }

}