package com.example.newsapp.views.newslist


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.model.entity.News
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io


class NewsViewModel : ViewModel() {

    private var newsListLiveData: MutableLiveData<MutableList<Article>> = MutableLiveData()
    private var newsList: MutableList<Article> = ArrayList()
    private var disposable: CompositeDisposable = CompositeDisposable()
    fun getLiveData(): LiveData<MutableList<Article>> {
        return newsListLiveData
    }


    fun getNews() : Observable<Article>{
        return RetrofitClient.requestApi
            .getNewsList("us", "c447104dd4014982b75313365151999f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.fromIterable(it.articles)
            }
        }

    fun getNewsData(){
        getNews().subscribeOn(io())
            ?.observeOn(io())
            ?.subscribe(object : Observer<Article> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }
                override fun onNext(t: Article) {
                    newsList.add(t)
                }
                override fun onError(e: Throwable) {

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