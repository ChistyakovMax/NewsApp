package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.model.entity.Article

@Database(entities = arrayOf(Article::class), version = 4, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "news_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}