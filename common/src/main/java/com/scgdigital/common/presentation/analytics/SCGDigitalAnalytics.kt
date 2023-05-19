package com.scgdigital.common.presentation.analytics

import com.scgdigital.common.domain.model.AnalyticsEvent

const val EVENT_AUTH_ATTEMPTED = "Auth Attempted"
const val EVENT_AUTH_ERROR = "Auth Error"
const val EVENT_ERROR_SHOWN = "Error Shown"
const val EVENT_BUTTON_PRESSED = "Button Pressed"
const val EVENT_DIALOG_SHOWN = "Dialog Shown"
const val EVENT_INPUT_ERRED = "Input Erred"
const val EVENT_SNACKBAR_SHOWN = "Snackbar Shown"
const val EVENT_NAVIGATION_PRESSED = "Navigation Pressed"
const val EVENT_SWITCH_PRESSED = "Switches Pressed"

const val EVENT_PROP_EVENT_KEY = "event_key"
const val EVENT_PROP_AUTH_DETAIL = "auth_detail"
const val EVENT_PROP_BUTTON_ACTION = "button_action"
const val EVENT_PROP_BUTTON_DETAIL = "button_detail"
const val EVENT_PROP_ERROR_DETAIL = "error_detail"
const val EVENT_PROP_ERROR_TYPE = "error_type"
const val EVENT_PROP_FLOW = "flow"
const val EVENT_PROP_CONTENT = "content"
const val EVENT_PROP_DIALOG_DETAIL = "dialog_detail"
const val EVENT_PROP_SNACKBAR_ACTION = "snackbar_action"
const val EVENT_PROP_SNACKBAR_DETAIL = "snackbar_detail"
const val EVENT_PROP_NAVIGATION_ACTION = "navigation_action"
const val EVENT_PROP_NAVIGATION_DETAIL = "navigation_detail"
const val EVENT_PROP_SWITCH_ACTION = "switches_action"
const val EVENT_PROP_SWITCH_DETAIL = "switches_detail"

const val EVENT_FULL_SCREEN_ERROR_API = "global_full_screen_error_api"
const val EVENT_PROP_ERROR = "error_events"
const val EVENT_PROP_ERROR_API_ROUTE = "error_api_route"

const val EVENT_MILESTONE_COMPLETE = "Milestone Complete"
const val EVENT_APPLICATION_STATUS = "application_status"

const val NAVIGATION_LOGIC_EVENT = "Navigation Logic"
const val NAVIGATION_LOGIC = "logic"
const val NAVIGATION_LOGIC_DETAILS = "logic_details"

interface OokbeeAnalytics {

    fun setIdentifier(guid: String? = null, params: Map<String, Any?> = emptyMap())
    fun trackOnEnterScreen(screen: String)
    fun trackEvent(screen: String, component: String?)
    fun trackEvent(event: String, params: Map<String, Any> = emptyMap())
    fun trackEvent(event: String, vararg params: Pair<String, Any>)
    fun trackEvent(analyticsEvent: AnalyticsEvent)
}
