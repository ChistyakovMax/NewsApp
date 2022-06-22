package com.example.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.model.entity.Article

@Database(entities = arrayOf(Article::class), version = 7, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDaoFun(): NewsDao

}