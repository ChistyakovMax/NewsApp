package com.example.newsapp.views.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.db.NewsDao
import com.example.newsapp.views.util.AppState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritesViewModel(val db: NewsDao) : ViewModel() {
    private var favoriteNews: MutableLiveData<AppState> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()
    fun getFavoriteNews(): LiveData<AppState>{
        return favoriteNews
    }


     fun getFavoriteNewsFromLocalStorage(){
         favoriteNews.value = AppState.Loading
         db.getFavorites()
             .subscribeOn(Schedulers.io())
             .observeOn(Schedulers.io())
             .subscribe({ favoriteNews.postValue(AppState.Success(it))})
    }
    fun clearAll(){
        disposable.add(db.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doAfterSuccess {}.subscribe()
        )
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}