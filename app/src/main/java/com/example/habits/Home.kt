package com.example.habits

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun TaskItem( task: Items, onDelete: () -> Unit ) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Plus"

            )

            Column {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = task.description, style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }

    }
    Spacer(modifier = Modifier.height(16.dp))



}

@Composable
fun Home(navController: NavController, onNavigateToAddTask: () -> Unit , viewModel: TaskViewModel ) {

    val tasks by viewModel.tasks.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Header()

        Spacer(modifier = Modifier.height(16.dp))



    LazyColumn {
        items(tasks) { task ->
            TaskItem(task = task, onDelete = {
                viewModel.deleteTask(task)

            })
        }

    }

    Button(
        onClick = onNavigateToAddTask
    ) {
        Text("Add Habits")
    }

}
}








