package io.embrace.android.embracesdk.internal.config.local

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WebViewLocalConfig(
    @Json(name = "enable") val captureWebViews: Boolean? = null,

    @Json(name = "capture_query_params") val captureQueryParams: Boolean? = null
)
