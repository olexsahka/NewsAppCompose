package com.loc.newsapp.data.remote

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.newsapp.domain.model.Article

class NewsPagingResource(
    private val newsApi: NewsApi,
    private val sources: String
) : PagingSource<Int, Article>() {
    private var totalNewsCount = 0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage =state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponce = newsApi.getNews(source = sources, page = page)
            totalNewsCount += newsResponce.articles.size
            val articles = newsResponce.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponce.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}