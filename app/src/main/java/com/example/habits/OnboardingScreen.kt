package com.example.habits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit , prefs: OnboardingPreferences) {
    val scope = rememberCoroutineScope()
    var page by remember { mutableIntStateOf(0) }

    val titles = listOf("Welcome to Habits" , "Track your tasks daily")
    val descriptions = listOf("Build good habits easily" , "Earn EXP and level up your life")

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(titles[page])
        Spacer(Modifier.height(16.dp))
        Text(descriptions[page])
        Spacer(Modifier.height(32.dp))

        Button(onClick = {
            if (page == titles.lastIndex) {
                scope.launch {
                    prefs.setOnboardingCompleted(true)
                    onFinish()
                }
                } else {
                    page++
                }

        }) {
            Text(if (page == titles.lastIndex) "get started" else "next")
        }
    }
}