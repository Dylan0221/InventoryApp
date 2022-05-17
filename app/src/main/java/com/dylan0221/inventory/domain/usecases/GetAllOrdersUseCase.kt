package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: InventoryRepository
) {

    suspend operator fun invoke(): Flow<List<OrderModel>> = flow {
        repository.getAllOrders().onEach {
            emit(it.map { it.toModel() })
        }.collect()
    }
}