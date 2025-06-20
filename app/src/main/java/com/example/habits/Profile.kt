package com.example.habits

import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Profile(viewModel: TaskViewModel = viewModel()) {

    BackgroundWrapper {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {


    AllItemsInShop(viewModel)
}

    }
}

@Composable
fun ShopItems(item: ShopItemData, onBuyClick: () -> Unit) {

    Spacer(modifier = Modifier.height(100.dp))
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(130.dp)
            .height(190.dp)
            .clickable { onBuyClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = item.description)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.cost.toString())
                    Icon(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "Coin",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}


@Composable
fun AllItemsInShop(viewModel: TaskViewModel) {
    val coin by viewModel.coin.collectAsState()
    val shopMessage by viewModel.shopMessage.collectAsState()
    val context = LocalContext.current
    val itemsInsideShop = listOf(
        ShopItemData(1, "DOUBLE EXP", "GIVES DOUBLE EXP", 70, effect = ShopEffect.DoubleXP),
        ShopItemData(
            2,
            "RESTORE HP",
            "RESTORE HEALTH TO FULL HP",
            20,
            effect = ShopEffect.IncreaseHealth
        ),
        ShopItemData(3, "Double gold", "GIVES DOUBLE GOLD", 100, effect = ShopEffect.DoubleCoin)
    )
    shopMessage?.let {message ->
        Toast.makeText(context,message , Toast.LENGTH_SHORT).show()
        viewModel.clearShopMessage()
    }
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "You have $coin")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.coin),
                contentDescription = "Coin",
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(itemsInsideShop) { item ->
                ShopItems(item = item) {
                    viewModel.buyingAnItem(item)

                }
            }
        }

    }
}