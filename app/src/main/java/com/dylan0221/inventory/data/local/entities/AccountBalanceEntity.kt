package com.dylan0221.inventory.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dylan0221.inventory.domain.model.AccountBalanceModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
data class AccountBalanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val balance: Int,
    val date: LocalDate

    ){
    fun toModel(): AccountBalanceModel =
        AccountBalanceModel(id, balance, date )


}
