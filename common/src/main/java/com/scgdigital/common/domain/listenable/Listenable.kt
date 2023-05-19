package com.scgdigital.common.domain.listenable

interface MutableListenable<T> : Listenable<T> {
    override var value: T
}

interface Listenable<T> {
    val value: T
    fun listen(immediate: Boolean, callBack: (T) -> Unit): Disposable
}

interface Disposable {
    fun dispose()
}
