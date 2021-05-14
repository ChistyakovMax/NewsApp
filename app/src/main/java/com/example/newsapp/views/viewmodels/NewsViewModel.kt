package com.example.newsapp.views.viewmodels


import androidx.lifecycle.ViewModel
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.model.entity.News
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NewsViewModel : ViewModel() {
    fun getNews() : Observable<News>{
        return RetrofitClient.requestApi.getNewsList("us", "c447104dd4014982b75313365151999f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

}