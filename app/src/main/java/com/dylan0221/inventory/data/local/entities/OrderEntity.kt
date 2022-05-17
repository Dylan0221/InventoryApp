package com.dylan0221.inventory.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.util.Constants.OrderTypes
import java.time.LocalDate
import java.util.*

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: OrderTypes,
    val date: LocalDate,
    val item: Int,
    val amount: Int,
    val price: Int,
){
    fun toModel(): OrderModel =
        OrderModel(id, type, date, item, amount, price)
}
