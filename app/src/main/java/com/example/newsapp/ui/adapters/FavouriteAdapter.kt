package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.data.db.Article
import com.example.newsapp.ui.fragments.FavouriteFragmentDirections

class FavouriteAdapter: ListAdapter<Article,FavouriteAdapter.FavouriteViewHolder>(ItemComparator()) {

    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: ConstraintLayout = view.findViewById(R.id.news_card)
        var title: TextView = view.findViewById(R.id.tv_title)
        private var description: TextView = view.findViewById(R.id.tv_description)
        private var date: TextView = view.findViewById(R.id.tv_date)
        var source: TextView = view.findViewById(R.id.tv_source_name)
        private var image: ImageView = view.findViewById(R.id.iv_news)

        fun onBind(article: Article) {
            title.text = article.title
            description.text = article.description
            date.text = article.publishedAt
            source.text = article.source
            image.load(article.urlToImage)
        }

        companion object {
            fun onCreate(parent: ViewGroup): FavouriteViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item,parent,false)

                return FavouriteViewHolder(view)
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.content == newItem.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder.onCreate(parent)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)

        holder.card.setOnClickListener {
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToReadingFragment(
                author = current.author,
                content = current.content,
                title = current.title,
                description = current.description,
                publishat = current.publishedAt,
                source = current.source,
                url = current.url,
                imageurl = current.urlToImage
            )
            holder.itemView.findNavController().navigate(action)
        }
    }
}