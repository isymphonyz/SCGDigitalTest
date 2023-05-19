package com.scgdigital.logictest

import androidx.annotation.StringRes
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.domain.SCGDigitalRoute
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.common.presentation.viewmodel.BaseStateViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics

data class LogicTestViewState(
    val showApplyButton: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = true,
    val error: SCGDigitalError? = null
)

sealed class LogicTestViewEvent {
    data class Navigate(override val route: String, override val clearBackStack: Boolean = true) :
        LogicTestViewEvent(), NavigationEvent

    data class ShowSnackbar(@StringRes val message: Int) : LogicTestViewEvent()

    data class OpenBottomSheet(val isBottomSheetVisible: Boolean) : LogicTestViewEvent()
}

class LogicTestViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private var isSessionTimeOut: Boolean,
    private val analytics: LogicTestAnalytics,
) : BaseStateViewModel<LogicTestViewState, LogicTestViewEvent>(
    coroutineDispatcherProvider,
    LogicTestViewState(),
) {

    fun onEnterScreen() {
        analytics.onEnterScreen()
    }

    fun onFindMiddleIndexClick() {
        analytics.onFindMiddleIndexClick()
        setEvent(LogicTestViewEvent.Navigate(route = SCGDigitalRoute.FIND_MIDDLE_INDEX, false))
    }

    fun onPalindromeClick() {
        analytics.onPalindromeClick()
        setEvent(LogicTestViewEvent.Navigate(route = SCGDigitalRoute.PALINDROME, false))
    }
}
