package com.dylan0221.inventory.di

import android.content.Context
import androidx.room.Room
import com.dylan0221.inventory.data.local.dao.InventoryDao
import com.dylan0221.inventory.data.local.database.InventoryDatabase
import com.dylan0221.inventory.data.repository.InventoryRepositoryImpl
import com.dylan0221.inventory.domain.repository.InventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): InventoryDatabase =
        Room.databaseBuilder(context, InventoryDatabase::class.java, "inventory_database")
            .fallbackToDestructiveMigration()
            .createFromAsset("database/inventory_database")
            .build()

    @Singleton
    @Provides
    fun providesDao(db: InventoryDatabase): InventoryDao = db.getInventoryDao()

    @Singleton
    @Provides
    fun providesRepository(dao: InventoryDao):InventoryRepository = InventoryRepositoryImpl(dao)
}