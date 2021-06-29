package com.example.newsapp.di.app

import android.app.Application
import com.example.newsapp.di.modules.AppModule
import com.example.newsapp.di.modules.RestModule

class App: Application() {
    companion object{
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

    }
}