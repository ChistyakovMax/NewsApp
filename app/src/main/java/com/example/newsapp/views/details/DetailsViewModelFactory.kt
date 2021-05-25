package com.example.newsapp.views.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.db.NewsDao
import java.lang.IllegalArgumentException

class DetailsViewModelFactory(private val db: NewsDao) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}