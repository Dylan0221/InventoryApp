package com.dylan0221.inventory.domain.repository

import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*

interface InventoryRepository {

    suspend fun newOrder(order: OrderEntity)

    suspend fun updateItem(item: ItemEntity)

    suspend fun newItem(item:ItemEntity)

    suspend fun deleteItem(item: ItemEntity)

    suspend fun getAllItems(): Flow<List<ItemEntity>>

    suspend fun getItemById(id: Int): Flow<ItemEntity>

    suspend fun getAllOrders(): Flow<List<OrderEntity>>

    suspend fun getOrdersByDate(date: LocalDate): Flow<List<OrderEntity>>

    suspend fun getLastAccountBalance(): Flow<AccountBalanceEntity>

    suspend fun getAllAccountBalance(): Flow<List<AccountBalanceEntity>>

    suspend fun updateAccountBalance(accountEntity: AccountBalanceEntity)

    suspend fun newAccountBalance(accountEntity: AccountBalanceEntity)


}