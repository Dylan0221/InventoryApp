package com.dylan0221.inventory.data.local.dao

import androidx.room.*
import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(ordersEntity: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntity: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountBalance(account: AccountBalanceEntity)

    @Update
    suspend fun updateItem(itemEntity: ItemEntity)

    @Update
    suspend fun updateAccountBalance(account: AccountBalanceEntity)

    @Delete
    suspend fun deleteItem(itemEntity: ItemEntity)

    @Query("SELECT * FROM ItemEntity")
    fun readAllItemEntity(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM ItemEntity WHERE id = :id")
    fun readItemEntityById(id: Int): Flow<ItemEntity>

    @Query("SELECT * FROM OrderEntity")
    fun readAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM OrderEntity WHERE date = :date ORDER BY date ASC")
    fun readOrderByDate(date: LocalDate):Flow<List<OrderEntity>>

    @Query("SELECT * FROM AccountBalanceEntity")
    fun readAllAccountBalance(): Flow<List<AccountBalanceEntity>>

    @Query("SELECT * FROM AccountBalanceEntity ORDER BY id DESC LIMIT 1")
    fun readLastAccountBalance(): Flow<AccountBalanceEntity>

}