package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsapp.data.api.NewsApiService
import com.example.newsapp.data.db.Article
import com.example.newsapp.data.db.ArticleDao
import com.example.newsapp.data.model.News
import com.example.newsapp.pagination.NewsPagingSource
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao
) {

    fun getNews() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { NewsPagingSource(newsApiService) }
    ).liveData


    private val _searched = MutableLiveData<News>()
    val searched: LiveData<News> = _searched

    val savedArticles = articleDao.getAllArticles()


    suspend fun searchNews(search: String) {
        try {
            val result = newsApiService.searchNews(q = search)
            if (result.body() != null) {
                _searched.postValue(result.body())
            }
        } catch (e: IOException) {

        }
    }

    suspend fun insert(article: Article) {
        articleDao.insert(article)
    }

    suspend fun delete(article: Article) {
        articleDao.delete(article)
    }
}