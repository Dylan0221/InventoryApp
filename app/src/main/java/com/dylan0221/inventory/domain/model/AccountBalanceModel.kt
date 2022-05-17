package com.dylan0221.inventory.domain.model

import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import java.time.LocalDate
import java.util.*

data class AccountBalanceModel (
    val id: Int = 0,
    val balance: Int,
    val date: LocalDate
    ){

    fun toEntity(): AccountBalanceEntity =
        AccountBalanceEntity(id, balance, date)
}