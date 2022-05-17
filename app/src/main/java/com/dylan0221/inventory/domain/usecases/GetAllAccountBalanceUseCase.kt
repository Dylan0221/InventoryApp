package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.AccountBalanceModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllAccountBalanceUseCase @Inject constructor(
    private val repository: InventoryRepository
) {

    suspend operator fun invoke(): Flow<List<AccountBalanceModel>> = flow {
        repository.getAllAccountBalance().onEach {
            emit(it.map { it.toModel() })
        }.collect()
    }
}