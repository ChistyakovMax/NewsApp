package com.example.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.entity.Article
import io.reactivex.Completable

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): List<Article>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: Article): Completable


}