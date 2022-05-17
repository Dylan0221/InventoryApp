package com.dylan0221.inventory.presentation.ui.screens

import java.util.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dylan0221.inventory.domain.model.AccountBalanceModel
import com.dylan0221.inventory.domain.model.OrderModel
import com.dylan0221.inventory.presentation.States.DayBalanceState
import com.dylan0221.inventory.presentation.ui.theme.Black_Coffee
import com.dylan0221.inventory.presentation.ui.theme.InventoryTheme
import com.dylan0221.inventory.presentation.ui.theme.Snow
import com.dylan0221.inventory.presentation.viewmodels.CalendarScreenViewModel
import com.dylan0221.inventory.util.Constants.OrderTypes
import io.github.boguszpawlowski.composecalendar.*
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import java.time.*
import java.time.format.TextStyle
import java.time.temporal.WeekFields

@Composable
fun CalendarScreen(viewModel: CalendarScreenViewModel = hiltViewModel()){



    Surface(modifier = Modifier.fillMaxSize(), color = Snow) {
        Column(Modifier.fillMaxSize()) {
            BalanceCalendar(
                dayContent = {
                    BalanceDay(
                        state = it,
                        viewModel.dayBalanceStateList.value
                    ){
                        viewModel.getOrdersByDate(it)
                    }
                },
                monthHeader = { CustomMonthHeader(monthState = it)},
                weekHeader = { CustomWeekHeader(daysOfWeek = it)}
            )
            OrdersList(list = viewModel.ordersList.value, items = viewModel.itemsMap.value)


        }
    }

}



@Preview
@Composable
fun CalendarScreenPreview() {

    val items = mapOf<Int, String>(
        0 to "Iphone",
        1 to "Silla",
        2 to "guitarra",
        3 to "perro",
        4 to "droga",
    )

    val orderList = listOf<OrderModel>(
        OrderModel(0,OrderTypes.BUY, LocalDate.now(),2,10,4000),
        OrderModel(0,OrderTypes.BUY, LocalDate.now(),4,1,10),
        OrderModel(0,OrderTypes.SELL, LocalDate.now(),1,10,100),
        OrderModel(0,OrderTypes.BUY, LocalDate.now(),0,100,100),
        OrderModel(0,OrderTypes.SELL, LocalDate.now(),2,1,1000)




    )

    InventoryTheme {
        BalanceCalendar()
    }
}

@Composable
fun OrdersList(modifier: Modifier = Modifier, list: List<OrderModel>, items: Map<Int, String>) {
    LazyColumn(){
        itemsIndexed(list){ index, order ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                elevation = 4.dp,
                backgroundColor = Snow
            ) {
                Column(modifier = Modifier.fillMaxHeight().padding(12.dp)){
                    Text(text = items[order.item ] ?: "Unknown Item", fontSize = 20.sp, fontWeight = Bold, color = Black_Coffee)
                    Text(text = "Amount: " + order.amount.toString(), fontSize = 10.sp, color = Color.DarkGray)
                    Text(text = "Price: " + order.price.toString(), fontSize = 10.sp, color = Color.DarkGray)
                }

                Column(modifier = Modifier.fillMaxSize().padding(12.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center) {
                    when(order.type){
                        OrderTypes.BUY ->
                            Text(text = "+$" + (order.amount * order.price).toString(),
                                color = Color.Green
                            )
                        OrderTypes.SELL ->
                            Text(text = "-$" + (order.amount * order.price).toString(),
                                color = Color.Red)
                    }
                }
            }
        }
    }
}





@Composable
fun BalanceDay(
    state: DayState<DynamicSelectionState>,
    dayBalanceStateList: List<DayBalanceState>,
    modifier: Modifier = Modifier,
    selectionColor: Color = MaterialTheme.colors.secondary,
    currentDayColor: Color = MaterialTheme.colors.primary,
    onClick: (LocalDate) -> Unit = {},
) {

    val dayBalanceState: DayBalanceState = if(dayBalanceStateList.isNotEmpty()){
        dayBalanceStateList.last()
    }else{
        DayBalanceState(AccountBalanceModel(balance = 0, date = LocalDate.now()))
    }

    
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(Snow),
        elevation = if (state.isFromCurrentMonth) 12.dp else 2.dp,
        border = if (state.isCurrentDay){
            BorderStroke(1.dp, currentDayColor)
        }else {
            null
        },
        contentColor = if (isSelected) {
            selectionColor
        } else contentColorFor(
            backgroundColor = Snow
        )
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick(date)
                    selectionState.onDateSelected(date)
                }
                .background(Snow),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = if(isSelected){
                    Color.Cyan
                }else {
                    if (dayBalanceState.checkPrevious(state.date) != null) {
                        when (dayBalanceState.checkPrevious(state.date)?.greater) {
                            true -> Color.Green
                            false -> Color.Red
                            else -> if(state.isFromCurrentMonth) Color.DarkGray else Color.LightGray
                        }
                    } else { if(state.isFromCurrentMonth) Color.DarkGray else Color.LightGray}
                }
            )
        }
    }
}

@Composable
fun BalanceCalendar(
    modifier: Modifier = Modifier,
    firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today: LocalDate = LocalDate.now(),
    showAdjacentMonths: Boolean = true,
    horizontalSwipeEnabled: Boolean = true,
    calendarState: CalendarState<DynamicSelectionState> = rememberSelectableCalendarState(),
    dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>, ) -> Unit = { DefaultDay(it) },
    monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
        Box { content(PaddingValues()) }
    },
) {
    Calendar(
        modifier = modifier,
        firstDayOfWeek = firstDayOfWeek,
        today = today,
        showAdjacentMonths = showAdjacentMonths,
        horizontalSwipeEnabled = horizontalSwipeEnabled,
        calendarState = calendarState,
        dayContent = dayContent,
        monthHeader = monthHeader,
        weekHeader = weekHeader,
        monthContainer = monthContainer
    )
}

@Composable
fun CustomMonthHeader(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        IconButton(
            modifier = Modifier.testTag("Decrement"),
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                colorFilter = ColorFilter.tint(Black_Coffee),
                contentDescription = "Previous",
            )
        }
        Text(
            modifier = Modifier.testTag("MonthLabel"),
            text = monthState.currentMonth.month
                .getDisplayName(TextStyle.FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.h4,
            color = Black_Coffee
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h4, color = Black_Coffee)
        IconButton(
            modifier = Modifier.testTag("Increment"),
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                colorFilter = ColorFilter.tint(Black_Coffee),
                contentDescription = "Next",
            )
        }
    }
}

@Composable
fun CustomWeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(),
                color = Black_Coffee
            )
        }
    }
}



