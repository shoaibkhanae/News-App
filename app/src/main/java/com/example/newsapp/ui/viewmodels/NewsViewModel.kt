package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.data.db.Article
import com.example.newsapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val news = repository.getNews().cachedIn(viewModelScope)
    val searched = repository.searched
    val saved = repository.savedArticles.asLiveData()



    fun searchNews(search: String) {
        viewModelScope.launch {
            repository.searchNews(search)
        }
    }

    fun insert(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(article)
        }
    }

    fun delete(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(article)
        }
    }
}