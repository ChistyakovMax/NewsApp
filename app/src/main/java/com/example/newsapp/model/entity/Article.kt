package com.example.newsapp.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "news_table")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String,
    @ColumnInfo(name = "type")
    var type: Int = 0
) : Parcelable