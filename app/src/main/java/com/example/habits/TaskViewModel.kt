package com.example.habits
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    private val _coin = MutableStateFlow(0)
    val coin: StateFlow<Int> = _coin

    private val _doubleXPLevel = MutableStateFlow(0)
    val doubleXPLevel: StateFlow<Int> = _doubleXPLevel

    private val _doubleCoinLevel = MutableStateFlow(0)
    val doubleCoinLevel : StateFlow<Int> = _doubleCoinLevel

    private val _shopMessage = MutableStateFlow<String?>(null)
    val shopMessage:StateFlow<String?> = _shopMessage

    val coinGainedPopUp = MutableStateFlow(0)






    init {
        loadTasks()
        loadLevel()
        loadHealth()
        loadExperience()
        loadCoin()
        loadDoubleExpLevel()
        loadDoubleCoinLevel()



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
        val baseExp = 0.1f
        val bonusMultiplier = 1 + (_doubleXPLevel.value * 0.3f)
        val gainedExp = baseExp * bonusMultiplier
        val newExperience = _experience.value + gainedExp
        _experience.value = newExperience
        saveExperience(newExperience)

        if(_experience.value >= 1f) {
            increaseLevel()
        }


        
    }
    fun loadCoin() {
        viewModelScope.launch {
            taskDataStore.getCoin().collect{
                _coin.value =it
            }
        }
    }

    fun saveCoin(coin:Int) {
        viewModelScope.launch {
            taskDataStore.saveCoin(coin = coin)

        }
    }

    fun increaseCoin() {
        val previousCoin = _coin.value
        val baseCoin = 3
        val bonusMultiplier = 1+ (_doubleCoinLevel.value *1)
        val gainedCoin = baseCoin * bonusMultiplier
        val newCoin = _coin.value + gainedCoin

        saveCoin(newCoin)
        showCoinGained(previousCoin , newCoin)
    }

     fun showCoinGained(previousCoin : Int , newCoin: Int) {
        val coinGained = newCoin - previousCoin
         coinGainedPopUp.value = coinGained

        Log.d("TaskViewModel", "you gained $coinGained coin")
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

    private fun loadDoubleExpLevel() {
        viewModelScope.launch {
            taskDataStore.getDoubleExpLevel().collect {
                _doubleXPLevel.value = it
            }
        }
    }
    private fun loadDoubleCoinLevel() {
        viewModelScope.launch {
            taskDataStore.getDoubleCoinLevel().collect{
                _doubleCoinLevel.value = it
            }
        }
    }


    fun buyingAnItem(item:ShopItemData) {
        if(_coin.value >=item.cost ) {
            val newCoin = _coin.value - item.cost
            saveCoin(newCoin)
            when(item.effect) {
                is ShopEffect.DoubleXP ->{
                    val newLevel = _doubleXPLevel.value +1
                    _doubleXPLevel.value = newLevel
                    viewModelScope.launch {
                        taskDataStore.saveDoubleExpLevel(newLevel)
                    }

                }
                is ShopEffect.IncreaseHealth -> {
                    IncreaseHealthToFull()

                }
                is ShopEffect.DoubleCoin -> {
                    val newDoubleCoin = _doubleCoinLevel.value + 1
                    _doubleCoinLevel.value = newDoubleCoin
                    viewModelScope.launch {
                        taskDataStore.saveDoubleCoinLevel(newDoubleCoin)
                    }

                }
            }
            _shopMessage.value = "Item Bought Successfully"
        } else {
            _shopMessage.value = "Not Enough Coin"
        }
    }
    fun clearShopMessage() {
        _shopMessage.value = null
    }







}

