package com.example.newsapp.views.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.db.NewsDao
import com.example.newsapp.db.NewsDatabase
import com.example.newsapp.di.app.App
import com.example.newsapp.model.api.RequestApi
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.views.util.AppState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject


class NewsViewModel @Inject constructor(var db: NewsDao, var requestApi: RequestApi ) : ViewModel() {

    private var appState: MutableLiveData<AppState> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()
    fun getAppState(): LiveData<AppState>{
        return appState
    }

    fun getNewsData(){

           var subscriber = requestApi
            .getNewsList("us", RetrofitClient.API_KEY)
            .subscribeOn(io())
            .observeOn(io())
            .doOnSubscribe {  appState.postValue(AppState.Loading) }
               .subscribe(
                {
                    appState.postValue(AppState.Success(it.articles))
                    disposable.add(
                        db.deleteCache()
                            .subscribe({},{})
                    )
                    for (item in it.articles){
                        disposable.add(db.insert(item)
                            .subscribeOn(io())
                            .observeOn(io())
                            .subscribe({},{})
                        )
                    }
                },
                { error ->
                    db.getAllNews().
                    subscribeOn(io())
                        .observeOn(io())
                        .subscribe({
                            appState.postValue(AppState.Success(it))
                        })
                })
            disposable.add(subscriber)

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}