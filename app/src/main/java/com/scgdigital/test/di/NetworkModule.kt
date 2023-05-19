package com.scgdigital.test.di

import com.scgdigital.news.data.api.ApiService
import com.scgdigital.news.data.repository.NewsRepositoryImpl
import com.scgdigital.news.domain.repository.NewsRepository
import com.scgdigital.news.domain.usecase.NewsUseCase
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L
private const val NEWS_URL = "https://newsapi.org"

val networkModule = module {
    single { createServiceNews(get(named("news"))) }
    single(named("news")) { createRetrofit(get(), NEWS_URL) }
    single { createOkHttpClient() }
    single { MoshiConverterFactory.create() }
    single { Moshi.Builder().build() }
}

val headerInterceptor = Interceptor { chain ->
    var request = chain.request()
    val requestBuilder = request.newBuilder()
    request = requestBuilder
        .build()
    chain.proceed(request)
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun createServiceNews(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createNewsRepository(apiService: ApiService): NewsRepository {
    return NewsRepositoryImpl(apiService)
}

fun createNewsUseCase(shopRepository: NewsRepository): NewsUseCase {
    return NewsUseCase(shopRepository)
}
