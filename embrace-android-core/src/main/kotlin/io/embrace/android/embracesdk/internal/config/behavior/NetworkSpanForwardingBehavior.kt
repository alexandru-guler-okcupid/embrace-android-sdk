package io.embrace.android.embracesdk.internal.config.behavior

interface NetworkSpanForwardingBehavior {
    // Here's where they are checking if network span forwarding is enabled
    fun isNetworkSpanForwardingEnabled(): Boolean
}
