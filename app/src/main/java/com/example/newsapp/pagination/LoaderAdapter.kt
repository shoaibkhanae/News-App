package com.example.newsapp.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class LoaderAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(view: View,retry: () -> Unit) : RecyclerView.ViewHolder(view) {
        private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        private val error: TextView = view.findViewById(R.id.error_msg)
        private val retryButton: Button = view.findViewById(R.id.retry)

        init {
            retryButton.setOnClickListener { retry.invoke() }
        }
        fun onBind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            error.isVisible = loadState is LoadState.Error
            retryButton.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                error.text = loadState.error.localizedMessage
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.loader_item,parent,false)

        return LoaderViewHolder(view,retry)
    }
}