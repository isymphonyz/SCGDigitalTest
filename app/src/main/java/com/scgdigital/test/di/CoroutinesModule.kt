package com.scgdigital.test.di

import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.data.executor.CoroutineDispatcherProviderImpl
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutineDispatcherProvider> { CoroutineDispatcherProviderImpl() }
}
