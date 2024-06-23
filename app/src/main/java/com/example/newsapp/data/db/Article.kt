package com.example.newsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.Source

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
