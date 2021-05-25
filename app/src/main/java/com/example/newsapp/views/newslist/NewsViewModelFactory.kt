package com.example.newsapp.views.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.db.NewsDao
import java.lang.IllegalArgumentException

class NewsViewModelFactory(private val db: NewsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}