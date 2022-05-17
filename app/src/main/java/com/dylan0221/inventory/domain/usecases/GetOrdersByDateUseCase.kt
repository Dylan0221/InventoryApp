package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class GetOrdersByDateUseCase @Inject constructor(
    private val repository: InventoryRepository
){

    suspend operator fun invoke(date: LocalDate): Flow<List<OrderModel>> = flow {
        repository.getOrdersByDate(date).onEach {
            emit(it.map { it.toModel() })
        }.collect()

    }

}