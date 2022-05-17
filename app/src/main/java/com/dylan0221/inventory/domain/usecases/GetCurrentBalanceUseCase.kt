package com.dylan0221.inventory.domain.usecases


import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCurrentBalanceUseCase @Inject constructor(
    private val repository: InventoryRepository
) {


    suspend operator fun invoke(): Flow<Int> = flow {
        try {
            repository.getLastAccountBalance().onEach {
                emit(it.balance)
            }.collect()
        }catch (e: Exception){
            emit(0)
        }

    }



}