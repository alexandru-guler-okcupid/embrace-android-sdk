package io.embrace.android.embracesdk.internal.injection

import io.embrace.android.embracesdk.internal.comms.api.ApiService

/**
 * Function that returns an instance of [DeliveryModule]. Matches the signature of the constructor for [DeliveryModuleImpl]
 */
typealias DeliveryModuleSupplier = (
    initModule: InitModule,
    storageModule: StorageModule,
    apiService: ApiService?,
) -> DeliveryModule

fun createDeliveryModule(
    initModule: InitModule,
    storageModule: StorageModule,
    apiService: ApiService?,
): DeliveryModule = DeliveryModuleImpl(
    initModule,
    storageModule,
    apiService,
)
