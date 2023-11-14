package io.embrace.android.embracesdk.fakes

import android.content.res.Configuration
import io.embrace.android.embracesdk.session.lifecycle.ProcessStateListener
import io.embrace.android.embracesdk.session.lifecycle.ProcessStateService

internal class FakeProcessStateService(
    override var isInBackground: Boolean = false,
) : ProcessStateService {

    val listeners: MutableList<ProcessStateListener> = mutableListOf()
    var config: Configuration? = null

    override fun addListener(listener: ProcessStateListener) {
        listeners.add(listener)
    }

    override fun close() {
    }

    override fun onForeground() {
    }

    override fun onBackground() {
    }
}
