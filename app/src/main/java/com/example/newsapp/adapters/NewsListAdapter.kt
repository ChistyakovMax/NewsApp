package com.example.newsapp.adapters


import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.entity.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_list_item.view.*
import org.w3c.dom.Text

class NewsListAdapter(private val context: Context) : RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    private var news : MutableList<Article> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, null, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(news.get(position))
        Picasso.with(context).load(news.get(position).urlToImage)
            .into(holder.newsImage)
    }
    fun setNews(news: MutableList<Article>){
        this.news = news
    }
    fun updateRecycler(){
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return news.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        var author: TextView
        var source: TextView
        var newsImage : ImageView
        init {
            title = itemView.header
            description = itemView.description
            author = itemView.header
            source = itemView.source
            newsImage = itemView.news_image
        }

        fun bind (news: Article){
            title.text = news.title
            description.text = news.title
            author.text = news.title
            source.text = news.title
            title.text = news.title
        }
    }
}