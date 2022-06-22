package com.example.newsapp.db

import androidx.room.*
import com.example.newsapp.model.entity.Article
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flowable<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(article: Article?): Completable

    @Query("DELETE FROM news_table WHERE type = 0")
    fun deleteCache() : Single<Integer>

    @Query("DELETE FROM news_table where type = 1 and title like :title")
    fun deleteNews(title: String) : Single<Integer>

    @Query("SELECT * FROM news_table WHERE type = 1")
    fun getFavorites() : Flowable<List<Article>>

    @Query("SELECT * FROM news_table WHERE type = 1 and title like :title")
    fun getNews(title: String) : Maybe<Article>

    @Query("SELECT COUNT(*) FROM news_table")
    fun getCountRecords() : Single<Integer>






}