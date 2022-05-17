package com.dylan0221.inventory.domain.model

import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.util.Constants.OrderTypes
import java.time.LocalDate
import java.util.*

data class OrderModel(
    val id: Int = 0,
    val type: OrderTypes,
    val date: LocalDate,
    val item: Int,
    val amount: Int,
    val price: Int,
    ) {


    fun toEntity(): OrderEntity =
        OrderEntity(id, type,date, item, amount, price)


}