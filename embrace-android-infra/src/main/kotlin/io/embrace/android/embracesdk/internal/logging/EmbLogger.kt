package io.embrace.android.embracesdk.internal.logging

/**
 * A simple interface that is used within the Embrace SDK for logging.
 */
interface EmbLogger {

    enum class Severity {
        DEBUG, INFO, WARNING, ERROR
    }

    var internalErrorService: InternalErrorHandler?

    /**
     * Logs a debug message with an optional throwable.
     */
    fun logDebug(msg: String, throwable: Throwable? = null)

    /**
     * Logs an informational message.
     */
    fun logInfo(msg: String, throwable: Throwable? = null)

    /**
     * Logs a warning message with an optional throwable.
     */
    fun logWarning(msg: String, throwable: Throwable? = null)

    /**
     * Logs a warning message with an optional error.
     */
    fun logError(msg: String, throwable: Throwable? = null)

    /**
     * Logs a warning message that the SDK is not yet initialized for the given action.
     */
    fun logSdkNotInitialized(action: String)

    /**
     * Tracks an internal error. This is sent to our own telemetry so should be used sparingly
     * & only for states that we can take actions to improve.
     */
    fun trackInternalError(type: InternalErrorType, throwable: Throwable)
}
