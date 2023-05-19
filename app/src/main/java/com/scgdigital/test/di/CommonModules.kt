package com.scgdigital.test.di

import com.scgdigital.common.AppStateRepository
import com.scgdigital.common.data.app.AppStateRepositoryImpl
import com.scgdigital.logictest.LogicTestViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalyticsImpl
import com.scgdigital.logictest.presentation.findmiddleindex.FindMiddleIndexViewModel
import com.scgdigital.logictest.presentation.palindrome.PalindromeViewModel
import com.scgdigital.news.presentation.NewsViewModel
import com.scgdigital.news.presentation.analytics.NewsAnalytics
import com.scgdigital.news.presentation.analytics.NewsAnalyticsImpl
import com.scgdigital.news.presentation.detail.NewsDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val commonModules
    get() = listOf(
        commonDataModule,
        commonDomainModule,
        commonPresentationModule,
    )

private val commonDataModule = module {
    single<AppStateRepository> { AppStateRepositoryImpl() }
}

private val commonDomainModule = module {

}

private val commonPresentationModule = module {
    factory<LogicTestAnalytics> { LogicTestAnalyticsImpl() }
    factory<NewsAnalytics> { NewsAnalyticsImpl() }
    viewModel { (isSessionTimeOut: Boolean) ->
        LogicTestViewModel(
            coroutineDispatcherProvider = get(),
            isSessionTimeOut = isSessionTimeOut,
            analytics = get(),
        )
    }
    viewModel { (isSessionTimeOut: Boolean) ->
        FindMiddleIndexViewModel(
            coroutineDispatcherProvider = get(),
            isSessionTimeOut = isSessionTimeOut,
            analytics = get(),
        )
    }
    viewModel { (isSessionTimeOut: Boolean) ->
        PalindromeViewModel(
            coroutineDispatcherProvider = get(),
            isSessionTimeOut = isSessionTimeOut,
            analytics = get(),
        )
    }
    viewModel { (isSessionTimeOut: Boolean) ->
        NewsViewModel(
            coroutineDispatcherProvider = get(),
            isSessionTimeOut = isSessionTimeOut,
            analytics = get(),
            newsUseCase = get()
        )
    }
    viewModel { (isSessionTimeOut: Boolean) ->
        NewsDetailViewModel(
            coroutineDispatcherProvider = get(),
            isSessionTimeOut = isSessionTimeOut,
            analytics = get()
        )
    }
}
