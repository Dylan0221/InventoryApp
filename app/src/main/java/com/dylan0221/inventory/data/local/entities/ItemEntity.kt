package com.dylan0221.inventory.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dylan0221.inventory.domain.model.ItemModel

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Int,
) {
    fun toModel(): ItemModel =
        ItemModel(id, name, amount)

}