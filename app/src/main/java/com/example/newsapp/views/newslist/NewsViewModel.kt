package com.example.newsapp.views.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.db.NewsDao
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.util.AppState
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io



class NewsViewModel(val db: NewsDao) : ViewModel() {

    private var newsListLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    private var newsList: MutableList<Article> = ArrayList()
    private var appState: MutableLiveData<AppState> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()
    fun getAppState(): LiveData<AppState>{
        return appState
    }


    fun getNews() : Observable<Article>{
        return RetrofitClient.requestApi
            .getNewsList("us", RetrofitClient.API_KEY)
            .subscribeOn(io())
            .observeOn(io())
            .flatMap {
                return@flatMap Observable.fromIterable(it.articles)
            }
        }

    fun getNewsData(){
        getNews().subscribeOn(io())
            .doOnSubscribe {  appState.postValue(AppState.Loading) }
            .doAfterTerminate { appState.postValue(AppState.Success(newsList)) }
            ?.observeOn(io())
            ?.subscribe(object : Observer<Article> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }
                override fun onNext(t: Article) {
                    newsList.add(t)
                    disposable.add(db.insert(t)
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                    )
                }
                override fun onError(e: Throwable) {
                    db.getAllNews().
                    subscribeOn(io())
                        .observeOn(io())
                        .subscribe({ newsListLiveData.postValue(it) })
                }
                override fun onComplete() {
                    newsListLiveData.postValue(newsList)
                }
            })


    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}