package com.example.newsapp.views.util

import com.example.newsapp.model.entity.Article

sealed class AppState {
    data class Success(val newsData: List<Article>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}