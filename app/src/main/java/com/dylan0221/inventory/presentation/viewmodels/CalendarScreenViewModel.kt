package com.dylan0221.inventory.presentation.viewmodels

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dylan0221.inventory.domain.model.AccountBalanceModel
import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.domain.usecases.usecase_packages.AllUseCases
import com.dylan0221.inventory.presentation.States.DayBalanceState
import com.dylan0221.inventory.util.Constants.TimeConstants.DAY_CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val useCase: AllUseCases
): ViewModel() {

    private val _ordersList: MutableState<List<OrderModel>> = mutableStateOf(emptyList())
    val ordersList: State<List<OrderModel>> = _ordersList

    private val _dayBalanceStateList: MutableState<MutableList<DayBalanceState>> =
        mutableStateOf(mutableListOf())
    val dayBalanceStateList: State<List<DayBalanceState>> = _dayBalanceStateList

    private val _itemsMap: MutableState<MutableMap<Int, String>> = mutableStateOf(mutableMapOf())
    val itemsMap: State<Map<Int, String>> = _itemsMap


    init {
        getAllAccountBalance()
        getItemsMap()
    }




    /*Need to create a function that uses a while loop, during the loop it gets the date of the current
    day -1 and if its null it continues the loop. if its not null it determines if the current date balance
    is greater or lesser then the past day balance and changes the ui accordingly(red or green)

    store the values in a list and use coroutines to get the range for the calendar.
     */


    private fun getAllAccountBalance(){

        viewModelScope.launch {
            useCase.getAllAccountBalanceUseCase().collect{

                it.forEachIndexed { index, accountBalanceModel ->
                    if (index > 0){
                        _dayBalanceStateList.value.add(
                            DayBalanceState(
                                value = accountBalanceModel,
                                previous = _dayBalanceStateList.value[index -1]
                            )
                        )
                    }else{
                        _dayBalanceStateList.value.add(
                            DayBalanceState(
                                value = accountBalanceModel
                            )
                        )
                    }
                }

            }
        }
    }

    fun getOrdersByDate(date: LocalDate){
        viewModelScope.launch {
            useCase.getOrdersByDateUseCase(date = date).collect{
                _ordersList.value = it
            }
        }
    }

    private fun getItemsMap(){
        viewModelScope.launch {
            useCase.getAllItemsUseCase().collect{ list ->
                list.forEach {
                    _itemsMap.value.put(it.id, it.name)
                }
            }
        }

    }

}