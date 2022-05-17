package com.dylan0221.inventory.domain.usecases.usecase_packages

import com.dylan0221.inventory.domain.usecases.*
import javax.inject.Inject

data class AllUseCases @Inject constructor(
    val deleteItemUseCase: DeleteItemUseCase,
    val getAllItemsUseCase: GetAllItemsUseCase,
    val getAllOrdersUseCase: GetAllOrdersUseCase,
    val getCurrentBalanceUseCase: GetCurrentBalanceUseCase,
    val newItemUseCase: NewItemUseCase,
    val newOrderUseCase: NewOrderUseCase,
    val updateBalanceUseCase: UpdateBalanceUseCase,
    val updateItemUseCase: UpdateItemUseCase,
    val getOrdersByDateUseCase: GetOrdersByDateUseCase,
    val getAllAccountBalanceUseCase: GetAllAccountBalanceUseCase,


)
