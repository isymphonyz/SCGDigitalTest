package com.scgdigital.test.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication

fun KoinApplication.scgDigitalKoin(
    application: Application
) {
    androidContext(application)
    modules(applicationModule)
    modules(coroutinesModule)
    modules(commonModules)
    modules(networkModule)
    modules(repositoryModule)
    modules(analyticModule)
}
