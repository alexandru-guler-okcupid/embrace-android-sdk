package io.embrace.android.embracesdk.testframework.assertions

import io.embrace.android.embracesdk.internal.payload.Attribute

internal fun List<Attribute>.toMap(): Map<String, String> {
    return associateBy { checkNotNull(it.key) }.mapValues { checkNotNull(it.value.data) }
}