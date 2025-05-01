package com.example.habits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskDetailScreen(task: Items) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Title: ${task.title}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${task.description}", style = MaterialTheme.typography.bodyLarge)
    }
}
