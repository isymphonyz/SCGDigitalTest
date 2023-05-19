package com.scgdigital.test.di

import com.scgdigital.news.presentation.analytics.NewsAnalytics
import com.scgdigital.news.presentation.analytics.NewsAnalyticsImpl
import org.koin.dsl.module

val analyticModule
    get() = listOf(
        analyticsModule,
    )

private val analyticsModule = module {
    factory<NewsAnalytics> { NewsAnalyticsImpl() }
}
