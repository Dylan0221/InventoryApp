package com.dylan0221.inventory.data.repository

import com.dylan0221.inventory.data.local.dao.InventoryDao
import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.data.local.entities.ItemEntity
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class FakeRepository @Inject constructor(
    @Named("test_dao")
    private val dao: InventoryDao
): InventoryRepository {




    override suspend fun newOrder(order: OrderEntity) =
        dao.insertOrder(order)



    override suspend fun updateItem(item: ItemEntity) =
        dao.updateItem(item)

    override suspend fun newItem(item: ItemEntity) =
        dao.insertItem(item)

    override suspend fun deleteItem(item: ItemEntity) =
        dao.deleteItem(item)

    override suspend fun getAllItems(): Flow<List<ItemEntity>> =
        dao.readAllItemEntity()

    override suspend fun getItemById(id: Int): Flow<ItemEntity> =
        dao.readItemEntityById(id)


    override suspend fun getAllOrders(): Flow<List<OrderEntity>> =
        dao.readAllOrders()

    override suspend fun getOrdersByDate(date: LocalDate): Flow<List<OrderEntity>> =
        dao.readOrderByDate(date)


    override suspend fun getLastAccountBalance(): Flow<AccountBalanceEntity> =
        dao.readLastAccountBalance()


    override suspend fun getAllAccountBalance(): Flow<List<AccountBalanceEntity>> =
        dao.readAllAccountBalance()

    override suspend fun updateAccountBalance(accountEntity: AccountBalanceEntity) =
        dao.updateAccountBalance(accountEntity)


    override suspend fun newAccountBalance(accountEntity: AccountBalanceEntity) =
        dao.insertAccountBalance(accountEntity)
}