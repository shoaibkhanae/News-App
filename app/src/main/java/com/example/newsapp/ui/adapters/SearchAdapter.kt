package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.ui.fragments.SearchFragmentDirections

class SearchAdapter(private val dataset: List<Article>)
    : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: ConstraintLayout
        var title: TextView
        var description: TextView
        var date: TextView
        var source: TextView
        var image: ImageView
        init {
            card = view.findViewById(R.id.news_card)
            title = view.findViewById(R.id.tv_title)
            description = view.findViewById(R.id.tv_description)
            date = view.findViewById(R.id.tv_date)
            source = view.findViewById(R.id.tv_source_name)
            image = view.findViewById(R.id.iv_news)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)

        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val current = dataset[position]

        holder.title.text = current.title
        holder.description.text = current.description
        holder.date.text = current.publishedAt
        holder.source.text = current.source.name
        holder.image.load(current.urlToImage)
        holder.card.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToReadingFragment(
                author = current.author,
                content = current.content,
                title = current.title,
                description = current.description,
                publishat = current.publishedAt,
                source = current.source.name,
                url = current.url,
                imageurl = current.urlToImage
            )
            holder.itemView.findNavController().navigate(action)
        }
    }
}