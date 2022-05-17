package com.dylan0221.inventory.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dylan0221.inventory.data.local.dao.InventoryDao
import com.dylan0221.inventory.data.local.database.InventoryDatabase
import com.dylan0221.inventory.data.repository.FakeRepository
import com.dylan0221.inventory.domain.repository.InventoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Named("test_database")
    @Provides
    fun provideTestDatabase(app: Application): InventoryDatabase =
        Room.inMemoryDatabaseBuilder(app, InventoryDatabase::class.java)
            .build()


    @Provides
    @Named("test_dao")
    fun providesTestDao(@Named("test_database") db: InventoryDatabase): InventoryDao = db.getInventoryDao()

    @Provides
    @Named("test_repo")
    fun provideTestRepository(@Named("test_dao") dao: InventoryDao): InventoryRepository = FakeRepository(dao)

}