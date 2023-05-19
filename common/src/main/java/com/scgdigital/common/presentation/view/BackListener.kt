package com.scgdigital.common.presentation.view

interface BackListener {

    // set true when you want to handle it yourself
    fun onClickBackButton(): Boolean {
        return false
    }

    fun onClickBackNavigation(): Boolean {
        return false
    }
}
