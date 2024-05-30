package io.embrace.android.embracesdk

import io.embrace.android.embracesdk.arch.schema.EmbType
import io.embrace.android.embracesdk.arch.schema.TelemetryType
import io.embrace.android.embracesdk.internal.payload.Attribute
import io.embrace.android.embracesdk.internal.payload.Log
import io.embrace.android.embracesdk.internal.payload.Span
import io.embrace.android.embracesdk.internal.spans.EmbraceSpanData
import io.embrace.android.embracesdk.internal.spans.hasFixedAttribute
import io.embrace.android.embracesdk.payload.SessionMessage
import io.embrace.android.embracesdk.spans.EmbraceSpanEvent

/**
 * Finds the first Span Event matching the given [TelemetryType]
 */
internal fun EmbraceSpanData.findEventOfType(telemetryType: TelemetryType): EmbraceSpanEvent {
    return findEventsOfType(telemetryType).single()
}

/**
 * Finds the Span Events matching the given [TelemetryType]
 */
internal fun EmbraceSpanData.findEventsOfType(telemetryType: TelemetryType): List<EmbraceSpanEvent> {
    return checkNotNull(events.filter { it.attributes.hasFixedAttribute(telemetryType) }) {
        "Events not found: $name"
    }
}

/**
 * Returns true if an event exists with the given [TelemetryType]
 */
internal fun EmbraceSpanData.hasEventOfType(telemetryType: TelemetryType): Boolean {
    return events.find { it.attributes.hasFixedAttribute(telemetryType) } != null
}

/**
 * Returns the Session Span
 */
internal fun SessionMessage.findSessionSpan(): EmbraceSpanData = findSpanOfType(EmbType.Ux.Session)

/**
 * Finds the span matching the given [TelemetryType].
 */
internal fun SessionMessage.findSpanOfType(telemetryType: TelemetryType): EmbraceSpanData {
    return findSpansOfType(telemetryType).single()
}

/**
 * Finds the span matching the given [TelemetryType].
 */
internal fun SessionMessage.findSpansOfType(telemetryType: TelemetryType): List<EmbraceSpanData> {
    return checkNotNull(spans?.filter { it.hasFixedAttribute(telemetryType) }) {
        "Spans of type not found: ${telemetryType.key}"
    }
}

/**
 * Finds the span matching the given [TelemetryType].
 */
internal fun SessionMessage.findSpansByName(name: String): List<Span> {
    return checkNotNull(data?.spans?.filter { it.name == name }) {
        "Spans not found named: $name"
    }
}

/**
 * Returns true if a span exists with the given [TelemetryType].
 */
internal fun SessionMessage.hasSpanOfType(telemetryType: TelemetryType): Boolean {
    return findSpansOfType(telemetryType).isNotEmpty()
}

internal fun SessionMessage.findSpanSnapshotsOfType(telemetryType: TelemetryType): List<Span> {
    val snapshots = checkNotNull(data?.spanSnapshots)
    return checkNotNull(snapshots.filter { it.hasFixedAttribute(telemetryType) }) {
        "Span snapshots of type not found: ${telemetryType.key}"
    }
}

internal fun Map<String, String>.findAttributeValue(key: String): String? {
    return get(key)
}

internal fun List<Attribute>.findAttributeValue(key: String): String? {
    return singleOrNull { it.key == key }?.data
}
