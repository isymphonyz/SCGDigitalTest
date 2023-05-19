package com.scgdigital.common.data.app

import com.scgdigital.common.AppStateRepository

class AppStateRepositoryImpl : AppStateRepository {
    override var isAppInForeground: Boolean = false
}
