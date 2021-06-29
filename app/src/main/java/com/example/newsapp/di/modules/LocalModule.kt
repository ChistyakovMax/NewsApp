package com.example.newsapp.di.modules

import android.app.Application
import androidx.room.Room
import com.example.newsapp.db.NewsDao
import com.example.newsapp.db.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): NewsDatabase {
        return Room
            .databaseBuilder(application, NewsDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideUserDao(appDataBase: NewsDatabase): NewsDao {
        return appDataBase.newsDaoFun()
    }


}