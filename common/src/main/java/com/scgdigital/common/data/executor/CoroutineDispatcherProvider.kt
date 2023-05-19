package com.scgdigital.common.data.executor

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}
