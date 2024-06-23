package com.example.newsapp.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.api.NewsApiService
import com.example.newsapp.data.model.Article
import okio.IOException
import retrofit2.HttpException

class NewsPagingSource(private val newsApiService: NewsApiService)
    : PagingSource<Int,Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 1
            val response = newsApiService.getNews(position)
            LoadResult.Page(
                data = response.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalResults) null else position + 1,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}