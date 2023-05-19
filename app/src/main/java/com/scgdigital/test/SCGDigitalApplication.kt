package com.scgdigital.test

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.scgdigital.test.di.scgDigitalKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import kotlin.coroutines.CoroutineContext

open class SCGDigitalApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + SupervisorJob()
    private val lifecycleObserver: SCGDigitalApplicationLifecycleObserver by inject()

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
        registerActivityLifecycleCallbacks(lifecycleObserver)
    }

    private fun setupKoin() {
        startKoin {
            scgDigitalKoin(this@SCGDigitalApplication)
        }
    }
}
