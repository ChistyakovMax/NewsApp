package com.example.newsapp.views.newslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.views.viewmodels.NewsViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.io
import java.util.*


class NewsListFragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       /* viewModel.getNews().flatMap {
            Observable.fromIterable(it.articles)
                .subscribeOn(io())
        }.doOnNext { Log.d("MyLog", it.author) }
            .doOnComplete { Log.d("MyLog", "Complete") }*/
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }


}