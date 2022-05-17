package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repository: InventoryRepository
) {

    suspend operator fun invoke(item: ItemModel) =
        repository.deleteItem(item.toEntity())
}