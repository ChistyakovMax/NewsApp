package com.example.newsapp.views.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.db.NewsDao
import com.example.newsapp.model.entity.Article
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class DetailsViewModel @Inject constructor(var db: NewsDao) : ViewModel() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    var checkPoint: MutableLiveData<Boolean> = MutableLiveData()
    fun saveArticleInLocalStorage(article: Article?) {
        if (article?.id == null) article?.id = 0
        if (article?.author == null) article?.author = ""
        if (article?.content == null) article?.content = ""
        if (article?.description == null) article?.description = ""
        if (article?.publishedAt == null) article?.publishedAt = ""
        if (article?.title == null) article?.title = ""
        if (article?.url == null) article?.url = ""
        if (article?.urlToImage == null) article?.urlToImage = ""
        disposable.add(db.insert(article)
            .subscribeOn(io())
            .observeOn(io())
            .subscribe({ checkPoint.postValue(true) },
                {
                    it.printStackTrace()
                }
            )
        )
    }

    fun getCheckPoint(): LiveData<Boolean> {
        return checkPoint
    }

    fun checkNewsInFavorite(article: Article) {
        val subs = db.getNews(article.title)
            .subscribeOn(io())
            .observeOn(io())
            .subscribe {
                checkPoint.postValue(true)
            }
        disposable.add(subs)
    }

    fun deleteArticleFromLocalStorage(article: Article) {
        disposable.add(
            db.deleteNews(article.title)
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