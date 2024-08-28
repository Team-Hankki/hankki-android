package com.hankki.core.common.amplitude

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.events.Identify
import com.hankki.core.common.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

val LocalTracker = staticCompositionLocalOf<AmplitudeTracker> {
    error("No AmplitudeTracker provided")
}

class AmplitudeTracker @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val amplitude: Amplitude = Amplitude(
        Configuration(
            apiKey = BuildConfig.AMPLITUDE_KEY,
            context = context.applicationContext,
        ),
    )

    fun track(type: EventType, name: String, properties: Map<String, Any?> = emptyMap()) {
        if (BuildConfig.DEBUG) {
            Timber.d("Amplitude: ${name}_${type.prefix} properties: $properties")
        }
        amplitude.track(eventType = "${name}_${type.prefix}", eventProperties = properties)
    }
}
