package com.example.newsapp.views.newslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsListAdapter
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.db.NewsDatabase
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.details.DetailsFragment
import com.example.newsapp.views.util.AppState
import com.example.newsapp.views.util.gone
import com.example.newsapp.views.util.visible
import java.util.*


class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NewsViewModel
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: NewsListAdapter
    private val listener = object : OnItemViewClickListener{
        override fun onItemViewClick(article: Article) {
            val manager = activity?.supportFragmentManager
            if(manager != null){
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, article)
                findNavController().navigate(R.id.detailsFragment, bundle)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = NewsDatabase.getDatabase(application).newsDao
        val viewModelFactory = NewsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        if(savedInstanceState == null){
            viewModel.getNewsData()
        }

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

        viewModel.getAppState().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.progress.gone()
                initRecyclerView(appState.newsData)
            }
            is AppState.Loading -> {
                binding.progress.visible()
            }
            is AppState.Error -> {
                binding.progress.gone()
            }
        }
    }
    fun initRecyclerView(news: List<Article>){
        adapter = NewsListAdapter(requireContext(), listener, news)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
    interface OnItemViewClickListener{
        fun onItemViewClick(article: Article)
    }

}