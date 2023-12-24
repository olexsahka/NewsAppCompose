package com.loc.newsapp.domain.useCases.news

import androidx.paging.PagingData
import com.loc.newsapp.data.remote.NewsApi
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

class SearchNews(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(searchQuery: String,sources: List<String>): Flow<PagingData<Article>> =
        newsRepository.searchNews(searchQuery,sources)}