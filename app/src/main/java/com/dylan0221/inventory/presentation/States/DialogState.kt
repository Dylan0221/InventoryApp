package com.dylan0221.inventory.presentation.States

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DialogState(
    var visibility: Boolean = false,
    val type: DialogType
) {

    enum class DialogType{
        ITEM,
        UPDATE,
        NEW_ORDER,
        BALANCE
    }

}