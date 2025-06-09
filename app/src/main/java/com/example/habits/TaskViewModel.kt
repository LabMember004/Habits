package com.example.habits
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.exp

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDataStore = TaskDataStore(application)

    private val _tasks = MutableStateFlow<List<Items>>(emptyList())
    val tasks: StateFlow<List<Items>> = _tasks

    private val _level = MutableStateFlow(1)
    val level: StateFlow<Int> = _level

    private val _health = MutableStateFlow(1f)
    val health: StateFlow<Float> = _health
    
    private val _experience = MutableStateFlow(1f)
    val experience: StateFlow<Float> = _experience




    init {
        loadTasks()
        loadLevel()
        loadHealth()
        loadExperience()



    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskDataStore.getTasks().collect{ savedTasks ->
                _tasks.value = savedTasks
            }
        }
    }

    fun addTask(title: String, description: String , createdAt: Long) {
        val newTask = Items(id = UUID.randomUUID().toString(), title = title, description = description , createdAt = createdAt)
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

     private fun loadLevel() {
         viewModelScope.launch {
             taskDataStore.getLevel().collect {
                 _level.value = it
             }
         }
     }

    fun saveLevel(level: Int) {
        viewModelScope.launch {
            taskDataStore.saveLevel(level = level)
        }
    }

    fun increaseLevel() {
        val newLevel = _level.value +1
        _level.value = newLevel
        resetExp()
        saveLevel(newLevel)


    }



    fun loadHealth() {
        viewModelScope.launch {
            taskDataStore.getHealth().collect{
                _health.value= it
            }
        }
    }
    fun saveHealth(health: Float) {
        viewModelScope.launch {
            taskDataStore.saveHealth(health = health)
        }
    }
    fun increaseHealth() {
        val newHealth = _health.value + 0.1f
        _health.value = newHealth
        saveHealth(newHealth)

    }

    fun decreaseHealth() {
        val newHealth = (_health.value - 0.1f).coerceAtLeast(0f)
        _health.value = newHealth
        saveHealth(newHealth)
    }
    fun loadExperience() {
        viewModelScope.launch {
            taskDataStore.getExperience().collect {
                _experience.value = it
            }
        }
    }

    fun saveExperience(experience: Float) {
        viewModelScope.launch { 
            taskDataStore.saveExperience(experience = experience)
        }
        
    }
    fun increaseExperience() {
        val newExperience = _experience.value + 0.5f
        _experience.value = newExperience
        saveExperience(newExperience)
        
    }
    fun resetExp() {
        val newExp = _experience.value - 1f
        _experience.value = newExp
        saveExperience(newExp)
    }
    fun resetLife() {

        val newLvl=1
        _level.value = newLvl
        saveLevel(newLvl)

        _experience.value = 0f
        saveExperience(0f)
        IncreaseHealthToFull()

    }
    private fun IncreaseHealthToFull() {
        val newHP = 1f
        _health.value = newHP
        saveHealth(newHP)
    }
    fun getPositiveClicksForTask(taskId:String): Flow<Int> {

        return taskDataStore.getPositiveClicksForEachTask(taskId)


    }
    fun increasePositiveClicksForTask(taskId:String) {
        viewModelScope.launch {

            val currentClicks = taskDataStore.getPositiveClicksForEachTask(taskId).firstOrNull() ?:0
            taskDataStore.savePositiveClicksForEachTask(taskId, currentClicks +1)
        }



    }






}

