package com.androiddevs.mvvmnewsapp.data.local.datasource

import com.androiddevs.mvvmnewsapp.data.local.db.ArticleDao
import com.androiddevs.mvvmnewsapp.data.models.ArticleEntity


class NewsLocalDataSource(val articleDao: ArticleDao) {
    suspend fun upsert(article: ArticleEntity) =
        articleDao.upsert(article)

    suspend fun getSavedNews(): List<ArticleEntity> =
        articleDao.getAllArticles()

    suspend fun deleteArticle(article: ArticleEntity) =
        articleDao.deleteArticle(article)

}