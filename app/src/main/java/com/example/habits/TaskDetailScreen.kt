package com.example.habits

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@Composable
fun TaskDetailScreen(task: Items, viewModel: TaskViewModel ) {


    val positiveClick by viewModel.positiveClicks.collectAsState()



    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Title: ${task.title}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${task.description}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "How many times you clicked on positive: $positiveClick" )



    }
}
