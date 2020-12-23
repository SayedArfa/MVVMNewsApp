package com.androiddevs.mvvmnewsapp.domain.models


import com.androiddevs.mvvmnewsapp.data.models.Source
import java.io.Serializable

data class Article(
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable