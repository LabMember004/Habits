package com.example.habits

import android.content.ClipData.Item
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "task_preferences")

class TaskDataStore(private val context: Context) {

    private val TASKS_KEY = stringPreferencesKey("tasks")
    val LEVEL_KEY = intPreferencesKey("level")
    val HEALTH_KEY = floatPreferencesKey("health")
    val EXPERIENCE_KEY = floatPreferencesKey("experience")


    suspend fun saveTasks(tasks: List<Items>) {
        val json = Gson().toJson(tasks)
        context.dataStore.edit { preferences ->
            preferences[TASKS_KEY] = json
        }
    }
    fun getTasks(): Flow<List<Items>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[TASKS_KEY] ?: "[]"
            val type = object : TypeToken<List<Items>>() {}.type
            Gson().fromJson(json , type) ?: emptyList()
        }
    }

    suspend fun saveLevel(level: Int) {
        context.dataStore.edit { preferences ->
            preferences[LEVEL_KEY] = level
        }
    }

    fun getLevel(): Flow<Int> {
        return context.dataStore.data
            .map { preferences ->
                preferences[LEVEL_KEY] ?:1
            }
    }

    suspend fun saveHealth(health: Float) {
        context.dataStore.edit { preferences->
            preferences[HEALTH_KEY] = health
        }
    }

    fun getHealth(): Flow<Float> {
        return context.dataStore.data
            .map { preferences ->
                preferences[HEALTH_KEY] ?: 1f
            }

    }

    suspend fun saveExperience(experience:Float) {
        context.dataStore.edit { preferences->
            preferences[EXPERIENCE_KEY] = experience
        }
    }

    fun getExperience(): Flow<Float> {
        return context.dataStore.data
            .map { preferences ->
                preferences[EXPERIENCE_KEY] ?: 1f
            }
    }
    suspend fun savePositiveClicksForEachTask(taskId:String , clicks: Int) {
        val key = intPreferencesKey("positive_clicks_$taskId")
        context.dataStore.edit { preferences ->
            preferences[key] = clicks
        }
    }
    fun getPositiveClicksForEachTask(taskId:String): Flow<Int> {
        val key = intPreferencesKey("positive_clicks_$taskId")
        return context.dataStore.data
            .map { preferences->
                preferences[key] ?: 0
            }
    }





}