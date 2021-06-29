package com.example.newsapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.di.scope.ViewModelKey
import com.example.newsapp.views.details.DetailsViewModel
import com.example.newsapp.views.factory.ViewModelFactory
import com.example.newsapp.views.favorites.FavoritesViewModel
import com.example.newsapp.views.newslist.NewsViewModel
import com.example.newsapp.views.serchingnews.SearchingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey( DetailsViewModel::class )
    abstract fun bindDetailsViewModel( mainViewModel: DetailsViewModel ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey( SearchingViewModel::class )
    abstract fun bindSearchingViewModel( mainViewModel: SearchingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey( NewsViewModel::class )
    abstract fun bindNewsViewModel( mainViewModel: NewsViewModel ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey( FavoritesViewModel::class )

    abstract fun bindFavoritesViewModel( mainViewModel: FavoritesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory( factory: ViewModelFactory):
            ViewModelProvider.Factory

}