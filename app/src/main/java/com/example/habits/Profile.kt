package com.example.habits

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Profile(viewModel: TaskViewModel = viewModel()) {

    AllItemsInShop(viewModel)
}







@Composable
fun ShopItems(item: ShopItemData , onBuyClick: () -> Unit) {
    Card() {
        Text(text = item.title)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = item.description)
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = item.cost.toString())
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onBuyClick) {
            Text("BUY")

        }


    }
}


@Composable
fun AllItemsInShop(viewModel: TaskViewModel) {

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