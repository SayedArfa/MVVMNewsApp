package com.androiddevs.mvvmnewsapp.domain.models
data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)