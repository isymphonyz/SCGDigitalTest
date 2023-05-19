package com.scgdigital.test.di

import org.koin.dsl.module

val repositoryModule = module {
    single { createNewsRepository(get()) }
    single { createNewsUseCase(get()) }
}
