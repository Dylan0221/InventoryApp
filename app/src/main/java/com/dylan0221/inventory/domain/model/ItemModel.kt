package com.dylan0221.inventory.domain.model

import com.dylan0221.inventory.data.local.entities.ItemEntity

data class ItemModel(
    val id: Int = 0,
    val name: String,
    val amount: Int,
) {
    fun toEntity(): ItemEntity =
        ItemEntity(id, name, amount)
}