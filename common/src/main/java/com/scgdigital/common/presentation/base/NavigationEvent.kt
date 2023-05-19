package com.scgdigital.common.presentation.base

interface NavigationEvent {

    val route: String
    val params: Map<String, Any?>? get() = null
    val clearBackStack: Boolean get() = false
}
