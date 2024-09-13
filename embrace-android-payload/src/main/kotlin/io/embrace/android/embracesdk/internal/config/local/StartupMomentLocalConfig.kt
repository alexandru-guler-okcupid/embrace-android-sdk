package io.embrace.android.embracesdk.internal.config.local

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents the startup moment configuration element specified in the Embrace config file.
 */
@JsonClass(generateAdapter = true)
class StartupMomentLocalConfig(

    @Json(name = "automatically_end") val automaticallyEnd: Boolean? = null
)
