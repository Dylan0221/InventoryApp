package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.AccountBalanceModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

import javax.inject.Inject

class UpdateBalanceUseCase @Inject constructor(
    private val repository: InventoryRepository
) {

    suspend operator fun invoke(newBalance: Int){

        val oldAccountBalance = repository.getLastAccountBalance().first()

        val newAccountBalance = AccountBalanceModel(oldAccountBalance.id,newBalance,oldAccountBalance.date)

        repository.updateAccountBalance(newAccountBalance.toEntity())
    }


}