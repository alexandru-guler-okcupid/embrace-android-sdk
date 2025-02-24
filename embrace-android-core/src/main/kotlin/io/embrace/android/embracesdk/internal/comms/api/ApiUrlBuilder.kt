package io.embrace.android.embracesdk.internal.comms.api

/**
 * Builds the API urls used to connect with Embrace endpoints.
 */
interface ApiUrlBuilder {

    /**
     * Returns the url used to fetch the config.
     */
    fun getConfigUrl(): String

    /**
     * Returns the url used to send data to Embrace.
     */
    fun getEmbraceUrlWithSuffix(apiVersion: String, suffix: String): String
}
