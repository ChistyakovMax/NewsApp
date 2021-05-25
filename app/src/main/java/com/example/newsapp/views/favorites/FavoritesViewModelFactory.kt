package com.example.newsapp.views.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.db.NewsDao
import java.lang.IllegalArgumentException

class FavoritesViewModelFactory(private val db: NewsDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}