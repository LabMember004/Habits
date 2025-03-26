package com.example.habits
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Items>>(emptyList())
    val tasks: StateFlow<List<Items>> = _tasks



    fun addTask(title: String, description: String) {
        val newTask = Items(id = UUID.randomUUID().toString(), title = title, description = description)
        _tasks.value = _tasks.value + newTask
    }

    fun deleteTask(taskId: String) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
    }
}

