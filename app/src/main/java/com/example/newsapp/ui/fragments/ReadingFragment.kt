package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.newsapp.data.db.Article
import com.example.newsapp.databinding.FragmentReadingBinding
import com.example.newsapp.ui.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingFragment : Fragment() {
    private var _binding: FragmentReadingBinding? = null
    val binding
        get() = _binding!!

    private val shareViewModel: NewsViewModel by activityViewModels()

    private lateinit var url: String
    private lateinit var author: String
    private lateinit var content: String
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var date: String
    private lateinit var source: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = ReadingFragmentArgs.fromBundle(it).url
            author = ReadingFragmentArgs.fromBundle(it).author.toString()
            content = ReadingFragmentArgs.fromBundle(it).content
            title = ReadingFragmentArgs.fromBundle(it).title
            description = ReadingFragmentArgs.fromBundle(it).description
            date = ReadingFragmentArgs.fromBundle(it).publishat
            source = ReadingFragmentArgs.fromBundle(it).source
            imageUrl = ReadingFragmentArgs.fromBundle(it).imageurl.toString()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadingBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        binding.webView.loadUrl(url)

        binding.saveButton.setOnClickListener { saveArticle() }
    }

    private fun saveArticle() {

        val article = Article(
            author = author,
            content = content,
            title = title,
            description = description,
            publishedAt = date,
            url = url,
            urlToImage = imageUrl,
            source = source
        )
        shareViewModel.insert(article)
        view?.let { Snackbar.make(it,"Article Saved",Snackbar.LENGTH_SHORT).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}