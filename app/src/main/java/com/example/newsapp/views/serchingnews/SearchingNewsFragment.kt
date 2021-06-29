package com.example.newsapp.views.serchingnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsListAdapter
import com.example.newsapp.databinding.FragmentSearchingNewsBinding
import com.example.newsapp.di.app.App
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.details.DetailsFragment
import com.example.newsapp.views.factory.ViewModelFactory
import com.example.newsapp.views.newslist.NewsListFragment
import com.example.newsapp.views.util.AppState
import com.example.newsapp.views.util.gone
import com.example.newsapp.views.util.visible
import javax.inject.Inject


class SearchingNewsFragment : Fragment() {
    private var _binding: FragmentSearchingNewsBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SearchingViewModel::class.java)
    }
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: NewsListAdapter
    private val listener = object : NewsListFragment.OnItemViewClickListener {
        override fun onItemViewClick(article: Article) {
            val manager = activity?.supportFragmentManager
            if(manager != null){
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, article)
                findNavController().navigate(R.id.detailsFragment, bundle)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.injectSearchNewsFragment(this)
        _binding = FragmentSearchingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun initRecyclerView(){
        adapter = NewsListAdapter(requireContext(), listener)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.searchingNewsRecyclerView)!!
        initRecyclerView()
        viewModel.getNews().observe(viewLifecycleOwner, {
            renderData(it)
        })
        binding.searchText.addTextChangedListener {
            if(it.toString().isNotEmpty()){
                viewModel.searchingData(it.toString())
            }

        }
    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.progress.gone()
                adapter.setData(appState.newsData)
            }
            is AppState.Loading -> {
                binding.progress.visible()
            }
            is AppState.Error -> {
                binding.progress.gone()

            }
        }
    }


}