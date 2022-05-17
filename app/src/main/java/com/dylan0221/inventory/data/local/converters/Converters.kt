package com.dylan0221.inventory.data.local.converters

import androidx.room.TypeConverter
import com.dylan0221.inventory.util.Constants.OrderTypes
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Converters {

    companion object{
        const val Buy = "BUY"
        const val Sell = "SELL"
    }

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun fromOrderTypeToString(order: OrderTypes): String{

        return when(order){
            OrderTypes.SELL -> Sell
            OrderTypes.BUY -> Buy
        }
    }

    @TypeConverter
    fun fromStringToOrderType(string: String): OrderTypes?{
        return when(string){
            Sell -> OrderTypes.SELL
            Buy -> OrderTypes.BUY
            else -> null
        }
    }
}