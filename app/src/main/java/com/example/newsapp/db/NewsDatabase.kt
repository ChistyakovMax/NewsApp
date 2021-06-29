package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.model.entity.Article

@Database(entities = arrayOf(Article::class), version = 6, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDaoFun(): NewsDao

}