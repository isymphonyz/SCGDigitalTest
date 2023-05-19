package com.scgdigital.logictest.presentation.palindrome

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.presentation.base.NavigationEvent
import com.scgdigital.common.presentation.error.SCGDigitalError
import com.scgdigital.common.presentation.viewmodel.BaseStateViewModel
import com.scgdigital.logictest.presentation.analytics.LogicTestAnalytics
import kotlinx.coroutines.launch

data class PalindromeViewState(
    val showApplyButton: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = true,
    val error: SCGDigitalError? = null,
    var isPalindrome: Boolean = false
)

sealed class PalindromeViewEvent {
    data class Navigate(override val route: String, override val clearBackStack: Boolean = true) :
        PalindromeViewEvent(), NavigationEvent

    data class ShowSnackbar(@StringRes val message: Int) : PalindromeViewEvent()

    data class OpenBottomSheet(val isBottomSheetVisible: Boolean) : PalindromeViewEvent()
}

class PalindromeViewModel(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private var isSessionTimeOut: Boolean,
    private val analytics: LogicTestAnalytics,
) : BaseStateViewModel<PalindromeViewState, PalindromeViewEvent>(
    coroutineDispatcherProvider,
    PalindromeViewState(),
) {

    fun onEnterScreen() {
        analytics.onEnterScreen()
    }

    fun onPalindromeClick(input: String) {
        analytics.onPalindromeClick()
        checkPalindrome(input)
    }

    private fun checkPalindrome(input: String) {
        val word = input.lowercase()
        val length = word.length
        var isPalindrome = true

        for (i in 0 until length/2) {
            if (word[i] != word[length - 1 - i]) {
                isPalindrome = false
                break
            }
        }

        setState {
            copy(
                isPalindrome = isPalindrome
            )
        }
    }
}
