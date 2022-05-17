package com.dylan0221.inventory.domain.usecases

import com.dylan0221.inventory.domain.model.AccountBalanceModel
import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.domain.repository.InventoryRepository
import com.dylan0221.inventory.util.Constants.OrderTypes
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class NewOrderUseCase @Inject constructor(
    private val repository: InventoryRepository
) {


    suspend operator fun invoke( itemId: Int, price: Int, amount: Int, types: OrderTypes) {

        val order = OrderModel(type = types, date = LocalDate.now(), item = itemId, amount = amount, price =  price)



        if (order.type == OrderTypes.BUY) {
            val oldBalance = repository.getLastAccountBalance().first()

            val newBalance = AccountBalanceModel(
                balance = oldBalance.balance - (order.price * order.amount),
                date = LocalDate.now()
            )

            val oldItem = repository.getItemById(order.item).first()

            val newItem = ItemModel(
                id = oldItem.id,
                amount = oldItem.amount + order.amount,
                name = oldItem.name
            )


            repository.newAccountBalance(newBalance.toEntity())

            repository.updateItem(newItem.toEntity())
        } else if (order.type == OrderTypes.SELL) {
            val oldBalance = repository.getLastAccountBalance().first()

            val newBalance = AccountBalanceModel(
                balance = oldBalance.balance + (order.price * order.amount),
                date = LocalDate.now()
            )
            val oldItem = repository.getItemById(order.item).first()

            val newItem = ItemModel(
                id = oldItem.id,
                amount = oldItem.amount - order.amount,
                name = oldItem.name
            )

            repository.newAccountBalance(newBalance.toEntity())

            repository.updateItem(newItem.toEntity())
        }
        repository.newOrder(order.toEntity())
    }


}