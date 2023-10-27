package io.embrace.android.embracesdk.comms.api

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import io.embrace.android.embracesdk.config.behavior.SdkEndpointBehavior
import io.embrace.android.embracesdk.logging.InternalStaticEmbraceLogger

internal class ApiUrlBuilder(
    private val enableIntegrationTesting: Boolean,
    private val isDebug: Boolean,
    private val sdkEndpointBehavior: SdkEndpointBehavior,
    private val appId: Lazy<String>,
    private val deviceId: Lazy<String>,
    context: Context,
) {
    companion object {
        private const val API_VERSION = 1
        private const val CONFIG_API_VERSION = 2
    }

    private val appVersionName = try {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName.toString().trim { it <= ' ' }
        versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "UNKNOWN"
    }

    private fun getConfigBaseUrl() =
        buildUrl(sdkEndpointBehavior.getConfig(appId.value), CONFIG_API_VERSION, "config")

    private fun getOperatingSystemCode() = Build.VERSION.SDK_INT.toString() + ".0.0"

    private fun getCoreBaseUrl(): String = if (isDebugBuild()) {
        sdkEndpointBehavior.getDataDev(appId.value)
    } else {
        sdkEndpointBehavior.getData(appId.value)
    }

    private fun isDebugBuild(): Boolean {
        return isDebug && enableIntegrationTesting && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())
    }

    private fun buildUrl(config: String, configApiVersion: Int, path: String): String {
        return "$config/v$configApiVersion/$path"
    }

    fun getConfigUrl(): String {
        return "${getConfigBaseUrl()}?appId=${appId.value}&osVersion=${getOperatingSystemCode()}" +
            "&appVersion=$appVersionName&deviceId=${deviceId.value}"
    }

    fun getEmbraceUrlWithSuffix(suffix: String): String {
        InternalStaticEmbraceLogger.logDeveloper(
            "ApiUrlBuilder", "getEmbraceUrlWithSuffix - suffix: $suffix"
        )
        return "${getCoreBaseUrl()}/v$API_VERSION/log/$suffix"
    }
}
