package com.example.habits

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Profile(viewModel: TaskViewModel = viewModel()) {

    AllItemsInShop(viewModel)
}







@Composable
fun ShopItems(item: ShopItemData , onBuyClick: () -> Unit) {
    Card() {
        Text(text = item.title)
        Text(text = item.description)
        Text(text = item.cost.toString())
        Button(onClick = onBuyClick) {

        }

    }
}


@Composable
fun AllItemsInShop(viewModel: TaskViewModel) {
    val coin by viewModel.coin.collectAsState()
    val itemsInsideShop = listOf(
        ShopItemData(1 , "DOUBLE EXP" , " GIVES DOUBLE EXP" , 20 , effect = ShopEffect.DoubleXP),
        ShopItemData(2 , "RESTORE HP" , " RESTORE HEALTH TO FULL HP" , 50 , effect = ShopEffect.IncreaseHealth)


    )

    LazyColumn {
        items(itemsInsideShop) { item ->
            ShopItems(item = item , onBuyClick = {
                viewModel.buyingAnItem(item)
            })
        }
    }
}