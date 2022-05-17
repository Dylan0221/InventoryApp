package com.dylan0221.inventory.domain.usecases

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.dylan0221.inventory.data.local.entities.ItemEntity
import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllItemsUseCase @Inject constructor(
    private val repository: InventoryRepository
) {

    suspend operator fun invoke(): Flow<List<ItemModel>> = flow {

        repository.getAllItems().onEach {
            emit(it.map { it.toModel() })
        }.collect()


    }
}