package io.embrace.android.embracesdk.fakes

import io.embrace.android.embracesdk.internal.session.MemoryCleanerListener

class FakeMemoryCleanerListener : MemoryCleanerListener {

    val callCount: Int get() = counter
    private var counter = 0

    override fun cleanCollections() {
        counter += 1
    }
}
