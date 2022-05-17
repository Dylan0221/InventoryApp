package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(
    private val repository: InventoryRepository
){

    suspend operator fun invoke(item: ItemModel, name: String? = null, amount: Int? = null){

        val newItem = ItemModel(
            id = item.id,
            amount = amount ?: item.amount,
            name = name ?: item.name
        )

        repository.updateItem(newItem.toEntity())
    }

}