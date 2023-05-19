package com.scgdigital.test.di

import com.scgdigital.test.SCGDigitalApplicationLifecycleObserver
import org.koin.dsl.module

val applicationModule = module {
    single { SCGDigitalApplicationLifecycleObserver(get()) }
}
