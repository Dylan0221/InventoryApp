package com.dylan0221.inventory.presentation.ui.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.dylan0221.inventory.presentation.States.DialogState
import com.dylan0221.inventory.presentation.ui.screens.*
import com.dylan0221.inventory.presentation.ui.theme.InventoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {


                MainScreen()
            }
        }
    }
}


