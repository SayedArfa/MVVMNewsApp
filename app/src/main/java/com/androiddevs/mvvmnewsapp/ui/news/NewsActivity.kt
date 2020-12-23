package com.androiddevs.mvvmnewsapp.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.data.local.datasource.NewsLocalDataSource
import com.androiddevs.mvvmnewsapp.data.local.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.remote.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.data.remote.datasource.NewsRemoteDataSource
import com.androiddevs.mvvmnewsapp.data.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.databinding.ActivityNewsBinding
import com.androiddevs.mvvmnewsapp.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_news.view.*


class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository =
            NewsRepository(
                NewsLocalDataSource(ArticleDatabase(this).getArticleDao()),
                NewsRemoteDataSource(RetrofitInstance.api),
                NetworkHelper(this)
            )
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        binding.bottomNavigationView.setupWithNavController(findNavController( R.id.newsNavHostFragment))
    }
}
