package com.example.newsapp.views.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsListAdapter
import com.example.newsapp.databinding.FragmentFavoritesNewsBinding
import com.example.newsapp.db.NewsDatabase
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.details.DetailsFragment
import com.example.newsapp.views.newslist.NewsListFragment
import com.example.newsapp.views.util.AppState
import com.example.newsapp.views.util.gone
import com.example.newsapp.views.util.visible


class FavoritesNewsFragment : Fragment() {
    private var _binding: FragmentFavoritesNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: FavoritesViewModel
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = NewsDatabase.getDatabase(application).newsDao
        val viewModelFactory = FavoritesViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        viewModel.getFavoriteNewsFromLocalStorage()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view?.findViewById(R.id.newsRecyclerView)!!
        viewModel.getFavoriteNews().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }
    fun initRecyclerView(news: List<Article>){
        adapter = NewsListAdapter(requireContext(), listener, news)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
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
                /* Snackbar
                     .make(
                         binding.mainFragmentFAB,
                         getString(R.string.error),
                         Snackbar.LENGTH_INDEFINITE
                     )
                     .setAction(getString(R.string.reload)) { viewModel.getWeatherFromLocalSourceRus() }
                     .show()*/
            }
        }
    }



}