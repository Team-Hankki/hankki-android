package com.hankki.core.common.amplitude

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
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

    fun track(type: EventType, name: String, properties: Map<PropertyKey, Any?> = emptyMap()) {
        val eventType = if (type == EventType.NONE) name else "${name}_${type.prefix}"
        val properties = properties.mapKeys { it.key.key }

        if (BuildConfig.DEBUG) {
            Timber.d("Amplitude: $eventType properties: $properties")
        }

        amplitude.track(eventType = eventType, eventProperties = properties)
    }
}
