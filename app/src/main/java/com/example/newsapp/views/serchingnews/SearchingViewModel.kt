package com.example.newsapp.views.serchingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.api.RequestApi
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.util.AppState
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class SearchingViewModel @Inject constructor(var requestApi: RequestApi) : ViewModel() {
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var appState : MutableLiveData<AppState> = MutableLiveData()


    fun getNews(): LiveData<AppState>{
        return appState
    }

    fun searchingData(request: String){
       val subscriber =  requestApi.searchForNews(request)
            .subscribeOn(io())
            .observeOn(io())
            .doOnSubscribe { appState.postValue(AppState.Loading) }
            .subscribe(
                {
                    appState.postValue(AppState.Success(it.articles))
                },
                {
                    it.printStackTrace()
                }
            )
        disposable.add(subscriber)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}