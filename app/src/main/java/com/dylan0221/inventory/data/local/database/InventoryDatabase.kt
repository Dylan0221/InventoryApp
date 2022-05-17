package com.dylan0221.inventory.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dylan0221.inventory.data.local.converters.Converters
import com.dylan0221.inventory.data.local.dao.InventoryDao
import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.data.local.entities.ItemEntity

@Database(
    entities = [
        AccountBalanceEntity::class,
        OrderEntity::class,
        ItemEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class InventoryDatabase: RoomDatabase() {

    abstract fun getInventoryDao(): InventoryDao
}