package io.embrace.android.embracesdk.fakes

import io.embrace.android.embracesdk.internal.capture.envelope.metadata.EnvelopeMetadataSource
import io.embrace.android.embracesdk.internal.payload.EnvelopeMetadata

internal class FakeEnvelopeMetadataSource : EnvelopeMetadataSource {

    var metadata: EnvelopeMetadata = EnvelopeMetadata()

    override fun getEnvelopeMetadata(): EnvelopeMetadata = metadata
}
