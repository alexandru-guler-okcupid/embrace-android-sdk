package io.embrace.android.embracesdk.internal.injection

import io.embrace.android.embracesdk.internal.anr.ndk.NativeAnrOtelMapper
import io.embrace.android.embracesdk.internal.anr.ndk.NativeThreadSamplerInstaller
import io.embrace.android.embracesdk.internal.anr.ndk.NativeThreadSamplerService
import io.embrace.android.embracesdk.internal.ndk.NativeCrashService
import io.embrace.android.embracesdk.internal.ndk.NdkService

interface NativeFeatureModule {
    val ndkService: NdkService
    val nativeThreadSamplerService: NativeThreadSamplerService?
    val nativeThreadSamplerInstaller: NativeThreadSamplerInstaller?
    val nativeAnrOtelMapper: NativeAnrOtelMapper
    val nativeCrashService: NativeCrashService
}
