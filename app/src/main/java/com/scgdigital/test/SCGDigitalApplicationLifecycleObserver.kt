package com.scgdigital.test

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.scgdigital.common.AppStateRepository
import java.lang.ref.WeakReference

class SCGDigitalApplicationLifecycleObserver(
    private val appStateRepository: AppStateRepository
) : LifecycleObserver, Application.ActivityLifecycleCallbacks {

    var currentActivity: WeakReference<Activity>? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForeground() {
        appStateRepository.isAppInForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        appStateRepository.isAppInForeground = false
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        /* nothing to do */
    }

    override fun onActivityStarted(activity: Activity) { /* nothing to do */ }

    override fun onActivityResumed(activity: Activity) { currentActivity = WeakReference(activity) }

    override fun onActivityPaused(activity: Activity) { /* nothing to do */ }

    override fun onActivityStopped(activity: Activity) { /* nothing to do */ }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { /* nothing to do */ }

    override fun onActivityDestroyed(activity: Activity) { /* nothing to do */ }
}
