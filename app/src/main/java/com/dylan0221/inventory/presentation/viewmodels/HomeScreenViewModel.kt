package com.dylan0221.inventory.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.domain.usecases.usecase_packages.AllUseCases
import com.dylan0221.inventory.util.Constants.OrderTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCase: AllUseCases
): ViewModel() {

    private val _balance: MutableState<Int> = mutableStateOf(0)
    val balance: State<Int> = _balance

    private val _itemsList: MutableState<List<ItemModel>> = mutableStateOf(emptyList())
    val itemsList: State<List<ItemModel>> = _itemsList

    private val _itemsMap: MutableState<MutableMap<String, Int>> = mutableStateOf(mutableMapOf())
    val itemsMap: State<Map<String, Int>> = _itemsMap


    init {


        getBalance()
        getItems()
    }


    fun decreaseItemAmount(item: ItemModel){
        viewModelScope.launch(Dispatchers.IO) {
            val newAmount = item.amount - 1
            useCase.updateItemUseCase(item = item, amount = newAmount
            )
        }

    }

    fun increaseItemAmount(item: ItemModel){
        viewModelScope.launch(Dispatchers.IO) {
            val newAmount = item.amount + 1
            useCase.updateItemUseCase(item = item, amount = newAmount )
        }

    }

    fun deleteItem(item: ItemModel){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.deleteItemUseCase(item)
        }
    }

    fun addNewItem(item: ItemModel){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.newItemUseCase(item)
        }
    }

    fun updateItem(item: ItemModel, name: String, amount: Int){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateItemUseCase(item, name, amount)
        }
    }

    fun updateBalance(newBalance: Int){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateBalanceUseCase(newBalance)
        }

    }

    fun newOrder(amount: Int, price: Int, item: Int, type: OrderTypes){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.newOrderUseCase(item,price,amount,type)
        }
    }

    private fun getBalance() {
        viewModelScope.launch{
            useCase.getCurrentBalanceUseCase().collect {
                _balance.value = it
            }
        }
    }

    private fun getItems(){
        viewModelScope.launch  {
            useCase.getAllItemsUseCase().collect{ list ->
                _itemsList.value = list
                getItemsMap(list)
            }
        }
    }

    private fun getItemsMap(list: List<ItemModel>){
        list.forEach {
            _itemsMap.value.put(it.name, it.id)
        }
    }
}







