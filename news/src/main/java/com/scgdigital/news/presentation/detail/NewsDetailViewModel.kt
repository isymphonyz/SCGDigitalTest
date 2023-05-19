package com.scgdigital.news.presentation.detail

import androidx.annotation.StringRes
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.common.presentation.viewmodel.BaseStateViewModel
import com.scgdigital.news.domain.model.Article
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class NewsDetailViewState(
    val showApplyButton: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = true,
    val error: SCGDigitalError? = null,
    val articles: List<Article>? = null
)

sealed class NewsDetailViewEvent {
    data class Navigate(override val route: String, override val clearBackStack: Boolean = true) :
        NewsDetailViewEvent(), NavigationEvent

    data class ShowSnackbar(@StringRes val message: Int) : NewsDetailViewEvent()

    data class OpenBottomSheet(val isBottomSheetVisible: Boolean) : NewsDetailViewEvent()
}

class NewsDetailViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private var isSessionTimeOut: Boolean,
    private val analytics: NewsAnalytics,
) : BaseStateViewModel<NewsDetailViewState, NewsDetailViewEvent>(
    coroutineDispatcherProvider,
    NewsDetailViewState(),
) {

    fun onEnterScreen() {
        analytics.onEnterScreen()
    }

    fun convertDateTime(dateTime: String): String {
        val instant = Instant.parse(dateTime)
        val localZoneId = ZoneId.systemDefault()
        val localDateTime = LocalDateTime.ofInstant(instant, localZoneId)

        val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", Locale.ENGLISH)
        return localDateTime.format(formatter)
    }
}
