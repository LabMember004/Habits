package com.example.habits

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Header(health: Float ,  experience: Float , level: Int , coin: Int ) {

    val groupIndex = level / 5

    val imageRes = levelImages.getOrNull(groupIndex) ?: R.drawable.ic_launcher_background




    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.pet_happy_state),
                contentDescription = "Test"
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                ProgressBar(progress = health , color = Color.Red)
                Text("Health")

                ProgressBar(progress = experience , color = Color.Yellow)
                Text("Experience")


            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "Level: $level")
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.coin) ,
                contentDescription = "Test",
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)

             )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = ": $coin")

        }
    }


}




@Composable
fun ProgressBar(progress: Float , color: Color) {
    LinearProgressIndicator(
        progress = progress,
        color = color,
        modifier = Modifier
            .height(20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
    )
}


