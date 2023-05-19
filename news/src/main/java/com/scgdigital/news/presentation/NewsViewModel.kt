package com.scgdigital.news.presentation

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.common.domain.model.ApiError
import com.scgdigital.common.domain.usecase.UseCaseResponse
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.common.presentation.viewmodel.BaseStateViewModel
import com.scgdigital.news.BuildConfig
import com.scgdigital.news.domain.model.Article
import com.scgdigital.news.domain.model.GetNewsRequestModel
import com.scgdigital.news.domain.model.NewsResponseModel
import com.scgdigital.news.domain.usecase.NewsUseCase
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import com.scgdigital.resource.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class NewsViewState(
    val showApplyButton: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = true,
    val error: SCGDigitalError? = null,
    val articles: List<Article>? = null
)

sealed class NewsViewEvent {
    data class Navigate(override val route: String, override val clearBackStack: Boolean = true) :
        NewsViewEvent(), NavigationEvent

    data class ShowSnackbar(@StringRes val message: Int) : NewsViewEvent()

    data class OpenBottomSheet(val isBottomSheetVisible: Boolean) : NewsViewEvent()
}

class NewsViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private var isSessionTimeOut: Boolean,
    private val analytics: NewsAnalytics,
    private val newsUseCase: NewsUseCase,
) : BaseStateViewModel<NewsViewState, NewsViewEvent>(
    coroutineDispatcherProvider,
    NewsViewState(),
) {

    var originalArticles: List<Article> = mutableListOf()

    fun onEnterScreen() {
        analytics.onEnterScreen()
    }

    fun performQuery(query: String) {
        val filterList = mutableListOf<Article>()
        originalArticles.forEach { article ->
            if (article.title!!.lowercase().contains(query.lowercase())) {
                filterList.add(article)
            }
        }
        if (filterList.size > 0) {
            setState {
                copy(
                    articles = filterList
                )
            }
        }
    }

    fun onNewsClick(article: Article) {
        val json = Uri.encode(Gson().toJson(article))
        setEvent(NewsViewEvent.Navigate(SCGDigitalRoute.NEWS_DETAIL + "/${json}", false))
    }

    fun convertDateTime(dateTime: String): String {
        val instant = Instant.parse(dateTime)
        val localZoneId = ZoneId.systemDefault()
        val localDateTime = LocalDateTime.ofInstant(instant, localZoneId)

        val formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm", Locale.ENGLISH)
        return localDateTime.format(formatter)
    }

    fun getNews(query: String) {
        setState {
            copy(
                isLoading = true,
            )
        }

        val currentDateTime = LocalDateTime.now().minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val getNewsRequestModel = GetNewsRequestModel(query, currentDateTime.format(formatter), "publishedAt", BuildConfig.TOKEN)
        newsUseCase.invoke(
            viewModelScope, getNewsRequestModel,
            object : UseCaseResponse<NewsResponseModel> {
                override fun onSuccess(result: NewsResponseModel) {
                    setState {
                        copy(
                            isLoading = false,
                            articles = result.articles
                        )
                    }
                    originalArticles = result.articles
                }

                override fun onError(apiError: ApiError?) {
                    setState {
                        copy(
                            isLoading = false,
                        )
                    }
                    setEvent(NewsViewEvent.ShowSnackbar(R.string.global_headline_full_screen_error_network))
                }
            },
        )
    }
}
