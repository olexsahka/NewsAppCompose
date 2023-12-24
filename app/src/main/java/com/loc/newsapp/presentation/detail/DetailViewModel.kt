package com.loc.newsapp.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.useCases.news.NewsUseCases
import com.loc.newsapp.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel(){
    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: DetailEvent){
        when (event){
            is DetailEvent.UpserDeleteArticel -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null){
                        upperArticle(event.article)
                    }
                    else
                        deleteArticle(event.article)
                }
            }
            is DetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }


    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = UIComponent.Toast("Article deleted")
    }

    private suspend fun upperArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect = UIComponent.Toast("Article Inserted")
    }
}