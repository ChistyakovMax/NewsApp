package com.example.newsapp.di.app

import android.app.Application
import com.example.newsapp.di.modules.AppModule
import com.example.newsapp.di.modules.LocalModule
import com.example.newsapp.di.modules.RestModule
import com.example.newsapp.di.modules.ViewModelModule
import com.example.newsapp.views.details.DetailsFragment
import com.example.newsapp.views.details.DetailsViewModel
import com.example.newsapp.views.favorites.FavoritesNewsFragment
import com.example.newsapp.views.favorites.FavoritesViewModel
import com.example.newsapp.views.newslist.NewsListFragment
import com.example.newsapp.views.newslist.NewsViewModel
import com.example.newsapp.views.serchingnews.SearchingNewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RestModule::class, LocalModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun injectNewsListFragment(newsListFragment: NewsListFragment)
    fun injectFavoritesFragment(favoriteNewsFragment: FavoritesNewsFragment)
    fun injectSearchNewsFragment(searchingNewsFragment: SearchingNewsFragment)
    fun injectDetailsFragment(detailsNewsFragment: DetailsFragment)


    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }
}