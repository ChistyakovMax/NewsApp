package com.example.newsapp.views.serchingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.util.AppState
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io

class SearchingViewModel : ViewModel() {
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var appState : MutableLiveData<AppState> = MutableLiveData()
    @Volatile
    private var newsList: MutableList<Article> = ArrayList()
    fun getNews(): LiveData<AppState>{
        return appState
    }

    fun searchingData(request: String){
        RetrofitClient.requestApi.searchForNews(request)
            .subscribeOn(io())
            .doOnSubscribe { appState.postValue(AppState.Loading) }
            .doAfterTerminate { appState.postValue(AppState.Success(newsList)) }
            .observeOn(io())
            .flatMap { return@flatMap Observable.fromIterable(it.articles)}
            .subscribe(object : Observer<Article>{
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                    newsList.clear()
                }
                override fun onError(e: Throwable) {
                    AppState.Error(e)
                }
                override fun onComplete() {
                }
                override fun onNext(t: Article) {
                    newsList.add(t)
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}