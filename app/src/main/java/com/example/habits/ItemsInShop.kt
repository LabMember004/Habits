package com.example.habits

data class ShopItemData(
    val id: Int,
    val title: String,
    val description: String,
    val cost: Int,
    val effect: ShopEffect

)


sealed class ShopEffect {
    object DoubleXP: ShopEffect()
    object IncreaseHealth: ShopEffect()
}