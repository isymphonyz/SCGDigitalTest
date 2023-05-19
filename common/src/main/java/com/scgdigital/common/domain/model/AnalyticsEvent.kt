package com.scgdigital.common.domain.model

abstract class AnalyticsEvent(val name: String) {
    protected val mutableParams: MutableMap<String, Any> = mutableMapOf()

    val params: Map<String, Any>
        get() = mutableParams
}
