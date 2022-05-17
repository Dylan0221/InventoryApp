package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.data.local.dao.InventoryDao
import com.dylan0221.inventory.domain.model.ItemModel
import javax.inject.Inject

class NewItemUseCase @Inject constructor(
    private val dao: InventoryDao
) {

    suspend operator fun invoke(itemModel: ItemModel) =
        dao.insertItem(itemModel.toEntity())

}