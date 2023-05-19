package com.scgdigital.logictest.presentation.findmiddleindex

import androidx.annotation.StringRes
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.common.presentation.viewmodel.BaseStateViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics

data class FindMiddleIndexViewState(
    val showApplyButton: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = true,
    val error: SCGDigitalError? = null,
    var index: Int = -2
)

sealed class FindMiddleIndexViewEvent {
    data class Navigate(override val route: String, override val clearBackStack: Boolean = true) :
        FindMiddleIndexViewEvent(), NavigationEvent

    data class ShowSnackbar(@StringRes val message: Int) : FindMiddleIndexViewEvent()

    data class OpenBottomSheet(val isBottomSheetVisible: Boolean) : FindMiddleIndexViewEvent()
}

class FindMiddleIndexViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private var isSessionTimeOut: Boolean,
    private val analytics: LogicTestAnalytics,
) : BaseStateViewModel<FindMiddleIndexViewState, FindMiddleIndexViewEvent>(
    coroutineDispatcherProvider,
    FindMiddleIndexViewState(),
) {

    fun onEnterScreen() {
        analytics.onEnterScreen()
    }

    fun onFindMiddleIndexClick(inputNumber: String) {
        analytics.onFindMiddleIndexClick()
        var numbers: List<Int>
        if (inputNumber.endsWith(",")) {
            numbers = inputNumber.substring(0, inputNumber.length - 1).split(",").map { it.toInt() }
        } else {
            numbers = inputNumber.split(",").map { it.toInt() }
        }
        findMiddleIndex(numbers)
    }

    private fun findMiddleIndex(arr: List<Int>) {
        var leftSum = 0
        var rightSum = arr.sum()
        var result = -1

        for (num in arr) {
            rightSum -= num

            if (leftSum == rightSum) {
                result = arr.indexOf(num)
                break
            }

            leftSum += num
        }
        setState {
            copy(
                index = result
            )
        }
    }
}
