package io.embrace.android.embracesdk.payload

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.embrace.android.embracesdk.internal.payload.EnvelopeMetadata
import io.embrace.android.embracesdk.internal.payload.EnvelopeResource
import io.embrace.android.embracesdk.internal.payload.SessionPayload
import io.embrace.android.embracesdk.internal.spans.EmbraceSpanData

/**
 * The session message, containing the session itself, as well as performance information about the
 * device which occurred during the session.
 */
@JsonClass(generateAdapter = true)
internal data class SessionMessage @JvmOverloads internal constructor(

    /**
     * The session information.
     */
    @Json(name = "s")
    val session: Session,

    /**
     * The device's performance info, such as power, cpu, network.
     */
    @Json(name = "p")
    val performanceInfo: PerformanceInfo? = null,

    @Json(name = "spans")
    val spans: List<EmbraceSpanData>? = null,

    @Json(name = "span_snapshots")
    val spanSnapshots: List<EmbraceSpanData>? = null,

    /*
     * Values below this point are copied temporarily from [Envelope]. Eventually we will migrate
     * everything to use [Envelope] and [SessionPayload] and remove this class,
     * but we'll keep it for now for backwards compat.
     */

    @Json(name = "resource")
    val resource: EnvelopeResource? = null,

    @Json(name = "metadata")
    val metadata: EnvelopeMetadata? = null,

    @Json(name = "version")
    val newVersion: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "data")
    val data: SessionPayload? = null
)

/**
 * Returns true if this message is a v2 payload. If so, it should be sent to a different
 * endpoint & handled differently.
 */
internal fun SessionMessage.isV2Payload() = data != null
