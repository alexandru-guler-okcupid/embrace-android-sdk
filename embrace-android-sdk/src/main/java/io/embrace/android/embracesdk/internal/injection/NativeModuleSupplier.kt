package io.embrace.android.embracesdk.internal.injection

/**
 * Function that returns an instance of [NativeModule]. Matches the signature of the constructor for [NativeModuleImpl]
 */
internal typealias NativeModuleSupplier = (
    initModule: InitModule,
    coreModule: CoreModule,
    storageModule: StorageModule,
    essentialServiceModule: EssentialServiceModule,
    payloadSourceModule: PayloadSourceModule,
    deliveryModule: DeliveryModule,
    androidServicesModule: AndroidServicesModule,
    workerThreadModule: WorkerThreadModule
) -> NativeModule

internal fun createNativeModule(
    initModule: InitModule,
    coreModule: CoreModule,
    storageModule: StorageModule,
    essentialServiceModule: EssentialServiceModule,
    payloadSourceModule: PayloadSourceModule,
    deliveryModule: DeliveryModule,
    androidServicesModule: AndroidServicesModule,
    workerThreadModule: WorkerThreadModule
): NativeModule = NativeModuleImpl(
    initModule,
    coreModule,
    storageModule,
    essentialServiceModule,
    payloadSourceModule,
    deliveryModule,
    androidServicesModule,
    workerThreadModule
)
