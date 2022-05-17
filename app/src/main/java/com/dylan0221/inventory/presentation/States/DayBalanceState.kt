package com.dylan0221.inventory.presentation.States

import com.dylan0221.inventory.domain.model.AccountBalanceModel
import io.github.boguszpawlowski.composecalendar.day.Day
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

data class DayBalanceState(
    val value: AccountBalanceModel,
    val previous: DayBalanceState? = null,
){
    val greater: Boolean? = if (previous != null) value.balance > previous.value.balance else null


    fun checkPrevious(date: LocalDate): DayBalanceState? =
        if(previous != null){
            if(previous.value.date == date){
                this
            }else {
                previous.checkPrevious(date)
            }
        }else{
            null
        }



}