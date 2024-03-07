package io.embrace.android.embracesdk.capture.crumbs

import io.embrace.android.embracesdk.arch.datasource.DataSourceImpl
import io.embrace.android.embracesdk.arch.destination.SessionSpanWriter
import io.embrace.android.embracesdk.arch.destination.SpanEventData
import io.embrace.android.embracesdk.arch.destination.SpanEventMapper
import io.embrace.android.embracesdk.arch.limits.UpToLimitStrategy
import io.embrace.android.embracesdk.arch.schema.EmbType
import io.embrace.android.embracesdk.config.ConfigService
import io.embrace.android.embracesdk.internal.clock.millisToNanos
import io.embrace.android.embracesdk.payload.CustomBreadcrumb

/**
 * Captures custom breadcrumbs.
 */
internal class CustomBreadcrumbDataSource(
    configService: ConfigService,
    writer: SessionSpanWriter
) : DataSourceImpl<SessionSpanWriter>(
    destination = writer,
    limitStrategy = UpToLimitStrategy(configService.breadcrumbBehavior::getCustomBreadcrumbLimit)
),
    SpanEventMapper<CustomBreadcrumb> {

    companion object {
        internal const val EVENT_NAME = "custom-breadcrumb"
        internal const val ATTR_KEY_MESSAGE = "message"
    }

    fun logCustom(message: String, timestamp: Long) {
        alterSessionSpan(
            inputValidation = {
                message.isNotEmpty()
            },
            captureAction = {
                val crumb = CustomBreadcrumb(message, timestamp)
                addEvent(crumb, ::toSpanEventData)
            }
        )
    }

    override fun toSpanEventData(obj: CustomBreadcrumb) = SpanEventData(
        EmbType.System.Breadcrumb,
        EVENT_NAME,
        obj.timestamp.millisToNanos(),
        mapOf("message" to (obj.message ?: ""))
    )
}
