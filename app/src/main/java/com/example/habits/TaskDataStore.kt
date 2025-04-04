package com.example.habits

import android.content.ClipData.Item
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "task_preferences")

class TaskDataStore(private val context: Context) {

    private val TASKS_KEY = stringPreferencesKey("tasks")

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
}