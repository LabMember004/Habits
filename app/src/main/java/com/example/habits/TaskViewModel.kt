package com.example.habits
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDataStore = TaskDataStore(application)

    private val _tasks = MutableStateFlow<List<Items>>(emptyList())
    val tasks: StateFlow<List<Items>> = _tasks


    init {
        loadTasks()

    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskDataStore.getTasks().collect{ savedTasks ->
                _tasks.value = savedTasks
            }
        }
    }

    fun addTask(title: String, description: String) {
        val newTask = Items(id = UUID.randomUUID().toString(), title = title, description = description)
        val updateList = _tasks.value + newTask

        _tasks.value = updateList

        viewModelScope.launch {
            taskDataStore.saveTasks(updateList)
        }
    }

    fun deleteTask(task: Items) {
        val updatedList = _tasks.value.filter { it != task }

        _tasks.value = updatedList

        viewModelScope.launch {
            taskDataStore.saveTasks(updatedList)
        }
    }
}

