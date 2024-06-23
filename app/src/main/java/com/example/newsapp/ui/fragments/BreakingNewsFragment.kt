package com.example.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.pagination.LoaderAdapter
import com.example.newsapp.pagination.NewsPagingAdapter
import com.example.newsapp.ui.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val adapter = NewsPagingAdapter()

        binding.recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter{ adapter.retry() },
            footer = LoaderAdapter{ adapter.retry() }
        )

        binding.btnRetry.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            binding.progressCircularBar.isVisible = loadState.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error

            val errorState = loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
            errorState?.let { Toast.makeText(requireContext(),"${it.error}",Toast.LENGTH_LONG).show() }
        }

        shareViewModel.news.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle,it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}