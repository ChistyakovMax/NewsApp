package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.model.api.RetrofitClient
import com.example.newsapp.model.entity.Article
import com.example.newsapp.model.entity.News
import com.example.newsapp.views.viewmodels.NewsViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setupWithNavController(nav_host_fragment_container.findNavController())
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        val retrofit = RetrofitClient.requestApi

        retrofit.getNewsList("us", "c447104dd4014982b75313365151999f")
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it.articles)
                .subscribeOn(io())}
            .subscribe(object : Observer<Article>{
                override fun onSubscribe(d: Disposable) {

                }
                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Article) {

                }


            });

    }
}