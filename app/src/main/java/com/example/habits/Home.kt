package com.example.habits

import android.app.Dialog
import android.util.Log
import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.exp


@Composable
fun TaskItem(
    task: Items,
    onIncreaseHealth: () -> Unit,
    onDecreaseHealth: () -> Unit,
    onIncreaseExperience: () -> Unit,
    onClick: () -> Unit,
    onIncreasePositiveClicks: () -> Unit,
    onIncreaseCoin: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfff0e4d8)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(45.dp)
                    .background(Color(0xffedbc8a))
                    .align(Alignment.CenterVertically)
                    .clickable { onDecreaseHealth() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable._491964_ui_minus_remove_delete_cancel_icon),
                    contentDescription = "minus",
                    modifier = Modifier.size(32.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = task.description, style = MaterialTheme.typography.bodySmall)
            }

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(45.dp)
                    .background(Color(0xffed7d0c))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onIncreaseHealth()
                        onIncreaseExperience()
                        onIncreasePositiveClicks()
                        onIncreaseCoin()


                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable._72525_plus_icon),
                    contentDescription = "plus",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))



}


@Composable
fun Home(navController: NavController, viewModel: TaskViewModel) {

    val tasks by viewModel.tasks.collectAsState()

    val level by viewModel.level.collectAsState()

    val health by viewModel.health.collectAsState()

    val experience by viewModel.experience.collectAsState()

    val coin by viewModel.coin.collectAsState()

    val coinGained by viewModel.coinGainedPopUp.collectAsState()


    var showDeathDialog by remember { mutableStateOf(false) }


    if (showDeathDialog) {
        Reaching0HpPopUpMessage(
            onDismissRequest = {
                showDeathDialog = false

            }
        )
    }




    LaunchedEffect(health) {
        when {
            health <= 0f -> {
                viewModel.resetLife()
                showDeathDialog = true
            }
        }
    }

    BackgroundWrapper {
        Column(modifier = Modifier.fillMaxSize()) {

        }

    Spacer(modifier = Modifier.height(16.dp))

    Column(modifier = Modifier.fillMaxSize()) {
        Header(health = health, experience = experience, level = level, coin = coin)

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text("Habits", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(25.dp))
        }
        LazyColumn {


            items(tasks) { task ->
                TaskItem(task = task,
                    onIncreaseHealth = {
                        if (health < 1f) {
                            viewModel.increaseHealth()
                        }
                    },
                    onDecreaseHealth = { if (health > 0f) viewModel.decreaseHealth() },
                    onIncreaseExperience = {
                        if (experience <= 1f) {
                            viewModel.increaseExperience()
                        }


                    },
                    onClick = {
                        navController.navigate("taskDetail/${task.id}")
                    },
                    onIncreasePositiveClicks = { viewModel.increasePositiveClicksForTask(task.id) },
                    onIncreaseCoin = { viewModel.increaseCoin() }


                )
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    }
        SnackBar(coinEarned = coinGained)

}
}

@Composable
fun Reaching0HpPopUpMessage(onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
            ,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "You have reached 0 health, therefore your pet died and you have to start at the beginning",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center
            )

        }
    }
}


@Composable
fun SnackBar(coinEarned: Int) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(coinEarned) {
        if (coinEarned > 0) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar("You Earned $coinEarned Coins")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),

            snackbar = { snackbarData ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF4CAF50))
                        .widthIn(max = 250.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = snackbarData.visuals.message,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )
    }
}
