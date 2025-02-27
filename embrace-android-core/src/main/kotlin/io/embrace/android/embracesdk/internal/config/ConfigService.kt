package io.embrace.android.embracesdk.internal.config

import io.embrace.android.embracesdk.internal.config.behavior.AnrBehavior
import io.embrace.android.embracesdk.internal.config.behavior.AppExitInfoBehavior
import io.embrace.android.embracesdk.internal.config.behavior.AutoDataCaptureBehavior
import io.embrace.android.embracesdk.internal.config.behavior.BackgroundActivityBehavior
import io.embrace.android.embracesdk.internal.config.behavior.BreadcrumbBehavior
import io.embrace.android.embracesdk.internal.config.behavior.DataCaptureEventBehavior
import io.embrace.android.embracesdk.internal.config.behavior.LogMessageBehavior
import io.embrace.android.embracesdk.internal.config.behavior.NetworkBehavior
import io.embrace.android.embracesdk.internal.config.behavior.NetworkSpanForwardingBehavior
import io.embrace.android.embracesdk.internal.config.behavior.SdkEndpointBehavior
import io.embrace.android.embracesdk.internal.config.behavior.SdkModeBehavior
import io.embrace.android.embracesdk.internal.config.behavior.SensitiveKeysBehavior
import io.embrace.android.embracesdk.internal.config.behavior.SessionBehavior
import io.embrace.android.embracesdk.internal.config.behavior.StartupBehavior
import io.embrace.android.embracesdk.internal.config.behavior.WebViewVitalsBehavior
import io.embrace.android.embracesdk.internal.payload.AppFramework

/**
 * Provides access to the configuration for the customer's app.
 *
 * Configuration is configured for the user's app, and exposed via the API.
 */
interface ConfigService {

    var remoteConfigSource: RemoteConfigSource?

    /**
     * How background activity functionality should behave.
     */
    val backgroundActivityBehavior: BackgroundActivityBehavior

    /**
     * How automatic data capture functionality should behave.
     */
    val autoDataCaptureBehavior: AutoDataCaptureBehavior

    /**
     * How automatic breadcrumb functionality should behave.
     */
    val breadcrumbBehavior: BreadcrumbBehavior

    /**
     * How log message functionality should behave.
     */
    val logMessageBehavior: LogMessageBehavior

    /**
     * How ANR functionality should behave.
     */
    val anrBehavior: AnrBehavior

    /**
     * How sessions should behave.
     */
    val sessionBehavior: SessionBehavior

    /**
     * How network call capture should behave.
     */
    val networkBehavior: NetworkBehavior

    /**
     * How the startup moment should behave
     */
    val startupBehavior: StartupBehavior

    /**
     * How the SDK should handle events where data can be captured. This could be a moment, etc...
     */
    val dataCaptureEventBehavior: DataCaptureEventBehavior

    /**
     * Provides whether the SDK should enable certain 'behavior' modes, such as 'integration mode'
     */
    val sdkModeBehavior: SdkModeBehavior

    /**
     * Provides base endpoints the SDK should send data to
     */
    val sdkEndpointBehavior: SdkEndpointBehavior

    /**
     * Provides whether the SDK should enable certain 'behavior' of web vitals
     */
    val webViewVitalsBehavior: WebViewVitalsBehavior

    /**
     * Provides behavior for the app exit info feature
     */
    val appExitInfoBehavior: AppExitInfoBehavior

    /**
     * How the network span forwarding feature should behave
     */
    val networkSpanForwardingBehavior: NetworkSpanForwardingBehavior

    /**
     * Provides behavior for keys that might be sensitive and should be redacted when they are sent to the server
     */
    val sensitiveKeysBehavior: SensitiveKeysBehavior

    /**
     * The app framework that is currently in use.
     */
    val appFramework: AppFramework

    /**
     * The Embrace app ID. This is used to identify the app within the database.
     */
    val appId: String?

    /**
     * Whether only OTel exporters should be used. If this returns true,
     * the SDK should avoid enabling unnecessary systems (such as anything that creates requests
     * to Embrace).
     */
    fun isOnlyUsingOtelExporters(): Boolean

    /**
     * Adds a listener for changes to the [RemoteConfig]. The listeners will be notified when the
     * [ConfigService] refreshes its configuration.
     *
     * @param configListener the listener to add
     */
    fun addListener(configListener: () -> Unit)

    /**
     * Checks if the SDK is enabled.
     *
     * The SDK can be configured to disable a percentage of devices based on the normalization of
     * their device ID between 1-100. This threshold is set in [RemoteConfig].
     *
     * @return true if the sdk is enabled, false otherwise
     */
    fun isSdkDisabled(): Boolean

    /**
     * Checks if the capture of background activity is enabled.
     *
     *
     * The background activity capture can be configured to enable a percentage of
     * devices based on the normalization of their device ID between 1-100.
     *
     * @return true if background activity capture is enabled.
     */
    fun isBackgroundActivityCaptureEnabled(): Boolean

    /**
     * Returns true if the remote config has been fetched and is not expired. Generally speaking
     * use of this function should be discouraged - but it can be useful to prevent running risky
     * behavior that should only be switched on via remote config.
     *
     * Most callers will not need this function - try not to abuse it.
     */
    fun hasValidRemoteConfig(): Boolean

    /**
     * Checks if the capture of Application Exit Info is enabled.
     *
     * @return true if AEI capture is enabled.
     */
    fun isAppExitInfoCaptureEnabled(): Boolean
}
