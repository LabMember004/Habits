package com.example.habits

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.exp


@Composable
fun TaskItem( task: Items, onDelete: () -> Unit, onIncreaseHealth: () -> Unit, onDecreaseHealth: () -> Unit , onIncreaseExperience: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable._491964_ui_minus_remove_delete_cancel_icon),
                contentDescription = "ss",

                modifier = imageModifier.clickable {
                    onDecreaseHealth()
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = task.description, style = MaterialTheme.typography.bodySmall)
            }

            Button(onClick = onDelete) {
                Text("Delete")
            }

            Image(
                painter = painterResource(id = R.drawable._72525_plus_icon),
                contentDescription = "ss",
                modifier = imageModifier.clickable {
                    onIncreaseHealth()
                    onIncreaseExperience()
                }
            )

        }

    }
    Spacer(modifier = Modifier.height(16.dp))



}

@Composable
fun Home(navController: NavController, onNavigateToAddTask: () -> Unit , viewModel: TaskViewModel ) {

    val tasks by viewModel.tasks.collectAsState()

    val level by viewModel.level.collectAsState()




    val health by viewModel.health.collectAsState()

    val experience by viewModel.experience.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Header(health = health , experience =experience , level = level )

        Spacer(modifier = Modifier.height(16.dp))



    LazyColumn {
        items(tasks) { task ->
            TaskItem(task = task, onDelete = {
                viewModel.deleteTask(task)},
                onIncreaseHealth = {if (health <1f) viewModel.increaseHealth()  },
                onDecreaseHealth = {if (health >0f) viewModel.decreaseHealth()},
                onIncreaseExperience = {
                    if(experience<1f) {
                        viewModel.increaseExperience()
                    } else {
                        viewModel.increaseLevel()
                        viewModel.resetExp()


                    }
                }



            )
        }

    }

    Button(
        onClick = onNavigateToAddTask
    ) {
        Text("Add Habits")
    }

}
}








