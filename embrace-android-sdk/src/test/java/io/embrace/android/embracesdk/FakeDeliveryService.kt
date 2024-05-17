package io.embrace.android.embracesdk

import io.embrace.android.embracesdk.comms.delivery.DeliveryService
import io.embrace.android.embracesdk.internal.payload.Envelope
import io.embrace.android.embracesdk.internal.payload.LogPayload
import io.embrace.android.embracesdk.internal.utils.Provider
import io.embrace.android.embracesdk.ndk.NativeCrashService
import io.embrace.android.embracesdk.payload.EventMessage
import io.embrace.android.embracesdk.payload.NetworkEvent
import io.embrace.android.embracesdk.payload.SessionMessage
import io.embrace.android.embracesdk.session.id.SessionIdTracker
import io.embrace.android.embracesdk.session.orchestrator.SessionSnapshotType

/**
 * A [DeliveryService] that records the last parameters used to invoke each method, and for the ones that need it, count the number of
 * invocations. Please add additional tracking functionality as tests require them.
 */
internal open class FakeDeliveryService : DeliveryService {
    var lastSentNetworkCall: NetworkEvent? = null
    var lastSentCrash: EventMessage? = null
    val lastSentLogs: MutableList<EventMessage> = mutableListOf()
    val lastSentLogPayloads: MutableList<Envelope<LogPayload>> = mutableListOf()
    val lastSavedLogPayloads: MutableList<Envelope<LogPayload>> = mutableListOf()
    val sentMoments: MutableList<EventMessage> = mutableListOf()
    var lastEventSentAsync: EventMessage? = null
    var eventSentAsyncInvokedCount: Int = 0
    var lastSavedCrash: EventMessage? = null
    var lastSentCachedSession: String? = null
    val sentSessionMessages: MutableList<Pair<SessionMessage, SessionSnapshotType>> = mutableListOf()
    val lastSavedSessionMessages: MutableList<Pair<SessionMessage, SessionSnapshotType>> = mutableListOf()

    override fun sendSession(sessionMessage: SessionMessage, snapshotType: SessionSnapshotType) {
        if (snapshotType != SessionSnapshotType.PERIODIC_CACHE) {
            sentSessionMessages.add(sessionMessage to snapshotType)
        }
        lastSavedSessionMessages.add(sessionMessage to snapshotType)
    }

    override fun sendCachedSessions(
        nativeCrashServiceProvider: Provider<NativeCrashService?>,
        sessionIdTracker: SessionIdTracker
    ) {
        lastSentCachedSession = sessionIdTracker.getActiveSessionId()
    }

    override fun sendMoment(eventMessage: EventMessage) {
        eventSentAsyncInvokedCount++
        lastEventSentAsync = eventMessage
        sentMoments.add(eventMessage)
    }

    override fun sendLog(eventMessage: EventMessage) {
        lastSentLogs.add(eventMessage)
    }

    override fun sendLogs(logEnvelope: Envelope<LogPayload>) {
        lastSentLogPayloads.add(logEnvelope)
    }

    override fun saveLogs(logEnvelope: Envelope<LogPayload>) {
        lastSavedLogPayloads.add(logEnvelope)
    }

    override fun sendNetworkCall(networkEvent: NetworkEvent) {
        lastSentNetworkCall = networkEvent
    }

    override fun sendCrash(crash: EventMessage, processTerminating: Boolean) {
        lastSavedCrash = crash
        lastSentCrash = crash
    }

    fun getSentSessions(): List<SessionMessage> {
        return sentSessionMessages.map { it.first }.filter { it.session.appState == "foreground" }
    }

    fun getSentBackgroundActivities(): List<SessionMessage> {
        return sentSessionMessages.map { it.first }.filter { it.session.appState == "background" }
    }

    fun getLastSavedSession(): SessionMessage? {
        return lastSavedSessionMessages.map { it.first }.lastOrNull { it.session.appState == "foreground" }
    }

    fun getLastSentSession(): SessionMessage? {
        return getSentSessions().lastOrNull()
    }
}
