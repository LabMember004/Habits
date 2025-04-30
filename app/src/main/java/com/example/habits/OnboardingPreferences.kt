package com.example.habits

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.datastore by preferencesDataStore(name = "onboarding_prefs")

class OnboardingPreferences(private val context: Context) {
    private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")

    val isOnboardingCompleted = context.datastore.data.map { prefs->
        prefs[ONBOARDING_KEY] ?: false
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.datastore.edit { prefs ->
            prefs[ONBOARDING_KEY] = completed
        }
    }
}