package com.example.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 2, exportSchema = false)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getDao(): ArticleDao
}