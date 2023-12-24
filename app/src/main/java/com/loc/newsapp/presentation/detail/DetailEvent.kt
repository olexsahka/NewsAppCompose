package com.loc.newsapp.presentation.detail

import com.loc.newsapp.domain.model.Article

sealed class DetailEvent {

    data class UpserDeleteArticel(val article: Article) : DetailEvent()
    object RemoveSideEffect : DetailEvent()

}