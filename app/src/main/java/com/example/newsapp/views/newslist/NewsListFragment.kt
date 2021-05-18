package com.example.newsapp.views.newslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsListAdapter
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.model.entity.Article
import com.example.newsapp.model.entity.News
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.util.*
import kotlin.collections.ArrayList


class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    var newsList: MutableList<Article> = ArrayList()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: NewsListAdapter
    private var disposable: CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsList = ArrayList()
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = view?.findViewById(R.id.newsRecyclerView)!!
        initRecyclerView()
        viewModel.getNewsData()
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            adapter.setNews(it)
            adapter.updateRecycler()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
    fun initRecyclerView(){
        adapter = NewsListAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }


}