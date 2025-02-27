package io.embrace.android.embracesdk.internal.config.instrumented

/**
 * Declares what features are enabled/disabled across the entire SDK.
 */
@Suppress("FunctionOnlyReturningConstant")
@Swazzled
object EnabledFeatureConfig {

    /**
     * Gates sigquit capture.
     *
     * sdk_config.anr.capture_google
     */
    fun isSigquitCaptureEnabled(): Boolean = false

    /**
     * Gates Unity ANR capture.
     *
     * sdk_config.anr.capture_unity_thread
     */
    fun isUnityAnrCaptureEnabled(): Boolean = false

    /**
     * Gates activity lifecycle breadcrumb capture.
     *
     * sdk_config.view_config.enable_automatic_activity_capture
     */
    fun isActivityBreadcrumbCaptureEnabled(): Boolean = true

    /**
     * Gates compose click capture
     *
     * sdk_config.compose.capture_compose_onclick
     */
    fun isComposeClickCaptureEnabled(): Boolean = false

    /**
     * Gates view click coordinate capture
     *
     * sdk_config.taps.capture_coordinates
     */
    fun isViewClickCoordinateCaptureEnabled(): Boolean = true

    /**
     * Gates memory warning capture
     *
     * sdk_config.automatic_data_capture.memory_info
     */
    fun isMemoryWarningCaptureEnabled(): Boolean = true

    /**
     * Gates power save mode capture
     *
     * sdk_config.automatic_data_capture.power_save_mode_info
     */
    fun isPowerSaveModeCaptureEnabled(): Boolean = true

    /**
     * Gates network connectivity capture
     *
     * sdk_config.automatic_data_capture.network_connectivity_info
     */
    fun isNetworkConnectivityCaptureEnabled(): Boolean = true

    /**
     * Gates ANR capture
     *
     * sdk_config.automatic_data_capture.anr_info
     */
    fun isAnrCaptureEnabled(): Boolean = true

    /**
     * Gates disk usage capture
     *
     * sdk_config.app.report_disk_usage
     */
    fun isDiskUsageCaptureEnabled(): Boolean = true

    /**
     * Gates JVM crash handler
     *
     * sdk_config.crash_handler.enabled
     */
    fun isJvmCrashCaptureEnabled(): Boolean = true

    /**
     * Gates native crash handler
     *
     * ndk_enabled
     */
    fun isNativeCrashCaptureEnabled(): Boolean = false

    /**
     * Gates AEI capture
     *
     * sdk_config.app_exit_info.aei_enabled
     */
    fun isAeiCaptureEnabled(): Boolean = true

    /**
     * Gates 3rd party signal handler detection
     *
     * sdk_config.sig_handler_detection
     */
    fun is3rdPartySigHandlerDetectionEnabled(): Boolean = true

    /**
     * Gates background activity capture
     *
     * sdk_config.background_activity.capture_enabled
     */
    fun isBackgroundActivityCaptureEnabled(): Boolean = false

    /**
     * Gates WebView breadcrumb capture
     *
     * sdk_config.webview.enable
     */
    fun isWebViewBreadcrumbCaptureEnabled(): Boolean = true

    /**
     * Gates query parameter capture in WebView breadcrumbs
     *
     * sdk_config.webview.capture_query_params
     */
    fun isWebViewBreadcrumbQueryParamCaptureEnabled(): Boolean = true

    /**
     * Gates whether the startup moment should automatically end
     *
     * sdk_config.startup_moment.automatically_end
     */
    fun isStartupMomentAutoEndEnabled(): Boolean = true

    /**
     * Gates whether the FCM feature should capture PII data
     *
     * sdk_config.capture_fcm_pii_data
     */
    fun isFcmPiiDataCaptureEnabled(): Boolean = false

    /**
     * Gates whether request content length should be captured
     *
     * sdk_config.networking.capture_request_content_length
     */
    fun isRequestContentLengthCaptureEnabled(): Boolean = false

    /**
     * Gates whether HttpUrlConnection network requests should be captured
     *
     * sdk_config.networking.enable_native_monitoring
     */
    fun isHttpUrlConnectionCaptureEnabled(): Boolean = true
}
