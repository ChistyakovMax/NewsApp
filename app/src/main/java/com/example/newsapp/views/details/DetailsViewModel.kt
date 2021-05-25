package com.example.newsapp.views.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.db.NewsDao
import com.example.newsapp.model.entity.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io

class DetailsViewModel(private val db : NewsDao) : ViewModel(){
    private var disposable: CompositeDisposable = CompositeDisposable()
    var checkPoint: MutableLiveData<Boolean> = MutableLiveData()
    fun saveArticleInLocalStorage(article: Article?){
        disposable.add(db.insert(article)
            .subscribeOn(io())
            .observeOn(io())
            .subscribe({
                checkPoint.postValue(true)
            })
        )
    }
    fun getCheckPoint(): LiveData<Boolean>{
        return checkPoint
    }
    fun checkNewsInFavorite(article: Article){
        db.getNews(article.title)
            .subscribeOn(io())
            .observeOn(io())
            .subscribe({
                checkPoint.postValue(true)
            })
    }
    fun deleteArticleFromLocalStorage(article: Article){
        disposable.add(db.deleteNews(article.title)
            .subscribeOn(io())
            .observeOn(io())
            .doAfterSuccess {
                checkPoint.postValue(false)
            }.subscribe()
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}