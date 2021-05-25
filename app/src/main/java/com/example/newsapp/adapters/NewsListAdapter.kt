package com.example.newsapp.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.entity.Article
import com.example.newsapp.views.newslist.NewsListFragment
import com.example.newsapp.views.util.gone
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_list_itemm.view.*
import kotlinx.android.synthetic.main.news_list_itemm.view.author
import kotlinx.android.synthetic.main.news_list_itemm.view.desc
import kotlinx.android.synthetic.main.news_list_itemm.view.img
import kotlinx.android.synthetic.main.news_list_itemm.view.publishedAt
import kotlinx.android.synthetic.main.news_list_itemm.view.title

class NewsListAdapter(private val context: Context, private val onItemViewClickListener: NewsListFragment.OnItemViewClickListener, private var news : List<Article> = ArrayList()) : RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.news_list_itemm, null, false)
        return MyViewHolder(view)
    }
    fun setData(list: List<Article>){
        news = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(news.get(position))
        Picasso.with(context).load(news.get(position).urlToImage)
            .fit()
            .into(holder.newsImage, object : Callback{
                override fun onSuccess() {
                    holder.progress.gone()
                }
                override fun onError() {
                    holder.progress.gone()
                }
            })
    }


    override fun getItemCount(): Int {
        return news.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var progress: ProgressBar
        var description: TextView
        var author: TextView
        var newsImage : ImageView
        var publishedAt : TextView
        init {
            title = itemView.title
            progress = itemView.item_progress_bar
            description = itemView.desc
            author = itemView.author
            publishedAt = itemView.publishedAt
            newsImage = itemView.img
        }

        fun bind (news: Article){
            title.text = news.title
            publishedAt.text = news.publishedAt.substring(0,10)
            description.text = news.description
            author.text = news.author
            title.text = news.title
            itemView.setOnClickListener {
                onItemViewClickListener.onItemViewClick(news)
            }
        }
    }
}