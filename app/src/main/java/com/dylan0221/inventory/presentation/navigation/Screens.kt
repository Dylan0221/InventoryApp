package com.dylan0221.inventory.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.dylan0221.inventory.R

sealed class Screens(
    val route: String,
    val title: String,
    val icon: Int
)
{
    object Home : Screens(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_baseline_home_24
    )

    object Calendar : Screens(
        route = "calendar",
        title = "Calendar",
        icon = R.drawable.ic_baseline_calendar_today_24
    )


}
