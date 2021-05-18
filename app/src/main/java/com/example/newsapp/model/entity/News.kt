package com.example.newsapp.model.entity

data class News(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)