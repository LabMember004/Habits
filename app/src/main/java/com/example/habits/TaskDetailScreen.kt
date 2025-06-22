package com.example.habits

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@Composable
fun TaskDetailScreen(task: Items, viewModel: TaskViewModel, navController: NavController, onNavigateBackToHome : () -> Unit) {


    val positiveClick by viewModel.getPositiveClicksForTask(task.id).collectAsState(initial = 0)



    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Going back to home page",modifier = Modifier.clickable {
            onNavigateBackToHome()
        })

        Text(text = "Title", style = MaterialTheme.typography.bodyLarge)

        Text(text = task.title)


        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Description", style = MaterialTheme.typography.bodyLarge)

        Text(text= task.description)

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "How many times you clicked on positive" )
        Text(text = positiveClick.toString())
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "This habit was created at")
        Text(text= formatTimestamp(task.createdAt))
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick= {
            viewModel.deleteTask(task)
            navController.navigate(Screens.Home.route)

        }
        ) {
            Text("Delete Task")

        }



    }
}
