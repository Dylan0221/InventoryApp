package com.dylan0221.inventory.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dylan0221.inventory.R
import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.presentation.viewmodels.HomeScreenViewModel
import com.dylan0221.inventory.presentation.States.DialogState
import com.dylan0221.inventory.presentation.ui.theme.*
import com.dylan0221.inventory.util.Constants.OrderTypes

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {

    val dialogState = remember { mutableStateOf(DialogState(type = DialogState.DialogType.ITEM)) }

    val currentItem: MutableState<ItemModel> = remember {
        mutableStateOf(ItemModel(0, "", 0))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Snow),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BalanceText(text = viewModel.balance.value, state = dialogState)



        Column(modifier = Modifier.fillMaxSize(.8f), verticalArrangement = Arrangement.Center) {
            ItemsList(
                items = viewModel.itemsList,
                viewModel = viewModel,
                dialog = dialogState,
                itemState = currentItem
            )
        }


        MainDialog(state = dialogState, viewModel = viewModel, itemModel = currentItem.value)


        Row(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = -50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            NewItemButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)
            ) {
                dialogState.value =
                    DialogState(visibility = true, type = DialogState.DialogType.ITEM)
            }

            NewOrderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                dialogState.value =
                    DialogState(visibility = true, type = DialogState.DialogType.NEW_ORDER)
            }


        }

    }
}


@Composable
fun BalanceText(text: Int, modifier: Modifier = Modifier, state: MutableState<DialogState>) {

    Text(
        text = "$$text",
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    state.value = DialogState(
                        visibility = true,
                        DialogState.DialogType.BALANCE
                    )
                }
            )
        },
        fontSize = 50.sp,
        color = Black_Coffee,
    )

}

@Preview
@Composable
fun ScreenPreview() {


    InventoryTheme {
    }
}


@Composable
fun ItemField(
    item: ItemModel,
    viewModel: HomeScreenViewModel,
    dialog: MutableState<DialogState>,
    itemState: MutableState<ItemModel>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        dialog.value = DialogState(
                            visibility = true,
                            type = DialogState.DialogType.UPDATE
                        )
                        itemState.value = item
                    }
                )
            }
            .padding(8.dp),
        elevation = 10.dp,
        backgroundColor = Snow
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Black_Coffee,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_left_24),
                    contentDescription = "decrease amount",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { viewModel.decreaseItemAmount(item) }
                )

                Text(text = item.amount.toString(), fontSize = 25.sp, color = Black_Coffee)

                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                    contentDescription = "increase amount",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { viewModel.increaseItemAmount(item) }
                )
            }
        }
    }
}


@Composable
fun NewOrderButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = onClick) {
        Text(text = "New Order")
    }
}


@Composable
fun NewItemButton(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "New Item")
    }
}

@Composable
fun ItemsList(
    items: State<List<ItemModel>>,
    viewModel: HomeScreenViewModel,
    dialog: MutableState<DialogState>,
    itemState: MutableState<ItemModel>
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        itemsIndexed(items.value) { index, item ->
            ItemField(item = item, viewModel = viewModel, dialog = dialog, itemState = itemState)
        }
    }

}


@Composable
fun TextFieldDropDownLayout(
    modifier: Modifier = Modifier,
    placeholder: String,
    label: String,
    value: String,
    isExpanded: MutableState<Boolean>
) {

    val icon =
        if (isExpanded.value) R.drawable.ic_baseline_arrow_drop_up_24 else R.drawable.ic_baseline_arrow_drop_down_24

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        maxLines = 1,
        modifier = modifier.background(color = Snow),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Steel_Blue,
            unfocusedBorderColor = Black_Coffee,
            focusedLabelColor = Steel_Blue,
            unfocusedLabelColor = Black_Coffee,
            placeholderColor = Black_Coffee,
            textColor = Black_Coffee,
            cursorColor = Steel_Blue
        ),
        trailingIcon = {
            Image(painter = painterResource(id = icon), "Icon for Dropdown menu",
                Modifier.clickable { isExpanded.value = !isExpanded.value })
        },
        readOnly = true,
    )
}

@Composable
fun TextFieldLayout(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    placeholder: String? = null,
    label: String? = null,
    value: MutableState<String>
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { if (label != null) Text(text = label) },
        placeholder = { if (placeholder != null) Text(text = placeholder) },
        maxLines = 1,
        modifier = modifier.background(color = Snow),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Steel_Blue,
            unfocusedBorderColor = Black_Coffee,
            focusedLabelColor = Steel_Blue,
            unfocusedLabelColor = Black_Coffee,
            placeholderColor = Black_Coffee,
            cursorColor = Steel_Blue,
            textColor = Black_Coffee
        ),
        keyboardOptions = keyboardOptions
    )
}


@Composable
fun DropDownMenuLayout(
    isExpanded: MutableState<Boolean>,
    list: List<String>,
    value: MutableState<String>,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero
) {


    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = { isExpanded.value = false },
        modifier = modifier.background(Snow),
        offset = offset,

    ) {
        list.forEach {
            DropdownMenuItem(onClick = {
                value.value = it
                isExpanded.value = false
            }) {
                Text(text = it)
            }
        }

    }
}

@Composable
fun MainDialog(
    modifier: Modifier = Modifier,
    state: MutableState<DialogState>,
    viewModel: HomeScreenViewModel,
    itemModel: ItemModel
) {

    if (state.value.visibility) {
        Dialog(onDismissRequest = {
            state.value = DialogState(visibility = false, DialogState.DialogType.ITEM)
        }) {
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .background(Snow)
            ) {
                if (state.value.visibility) {
                    when (state.value.type) {
                        DialogState.DialogType.ITEM -> ItemDialog(state = state)
                        DialogState.DialogType.UPDATE -> UpdateDialog(
                            viewModel = viewModel,
                            item = itemModel,
                            state = state
                        )
                        DialogState.DialogType.NEW_ORDER -> OrderDialog(
                            viewModel = viewModel,
                            state = state
                        )
                        DialogState.DialogType.BALANCE -> UpdateBalanceDialog(viewModel = viewModel, state = state)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDialog(viewModel: HomeScreenViewModel = hiltViewModel(), state: MutableState<DialogState>) {

    val itemName = remember { mutableStateOf("") }
    val itemAmount = remember { mutableStateOf("0") }
    val errorVisibility = remember { mutableStateOf(false) }

    val spacerSize = 40.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            placeholder = "Name",
            label = "Item",
            value = itemName,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(spacerSize))
        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = itemAmount.value,
            label = "Amount",
            value = itemAmount,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(spacerSize))
        Button(
            onClick = {
                if (itemName.value.isEmpty() || itemAmount.value.isEmpty()) {
                    errorVisibility.value = true

                } else {
                    val newItem =
                        ItemModel(name = itemName.value, amount = itemAmount.value.toInt())
                    viewModel.addNewItem(newItem)
                    state.value = DialogState(type = DialogState.DialogType.ITEM)

                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add New Item")
        }

        Spacer(modifier = Modifier.size(spacerSize))
        if (errorVisibility.value) {
            Text(text = "There is an empty field. Please try again", color = Imperial_Red)

        }
    }
}

@Composable
fun UpdateDialog(
    viewModel: HomeScreenViewModel,
    item: ItemModel,
    state: MutableState<DialogState>
) {

    val itemName = remember { mutableStateOf(item.name) }
    val itemAmount = remember { mutableStateOf(item.amount.toString()) }
    val errorVisibility = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            placeholder = "Name",
            label = "Item",
            value = itemName,
            modifier = Modifier.fillMaxWidth()
        )
        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = itemAmount.value,
            label = "Amount",
            value = itemAmount,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (itemName.value.isEmpty() || itemAmount.value.isEmpty()) {
                    errorVisibility.value = true

                } else {
                    viewModel.updateItem(item, itemName.value, itemAmount.value.toInt())
                    state.value = DialogState(
                        visibility = false,
                        type = DialogState.DialogType.UPDATE
                    )
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update Item")
        }
        Button(onClick = {
            viewModel.deleteItem(item)
            state.value = DialogState(
                visibility = false,
                type = DialogState.DialogType.UPDATE
            )

        }, colors = ButtonDefaults.buttonColors(backgroundColor = Imperial_Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Delete Item")
        }
        if (errorVisibility.value) {
            Text(text = "There is an empty field. Please try again", color = Imperial_Red)

        }
    }
}

@Composable
fun OrderDialog(viewModel: HomeScreenViewModel, state: MutableState<DialogState>) {
    val orderTypes = listOf<String>("Buy", "Sell")
    val orderPrice = remember { mutableStateOf("0") }
    val orderAmount = remember { mutableStateOf("0") }
    val orderType = remember { mutableStateOf(orderTypes[0]) }
    val isOrderTypeExpanded = remember { mutableStateOf(false) }


    val items = viewModel.itemsList.value.map { it.name }


    val itemName = remember { mutableStateOf(items[0]) }
    val errorVisibility = remember { mutableStateOf(false) }
    val isItemNameExpanded = remember { mutableStateOf(false) }

    val spacerSize = 100.dp



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box() {
            TextFieldDropDownLayout(
                value = itemName.value,
                label = "Item",
                isExpanded = isItemNameExpanded,
                placeholder = items[0]
            )

            DropDownMenuLayout(
                isExpanded = isItemNameExpanded,
                list = items,
                value = itemName
            )
        }


        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = orderAmount,
            label = "Amount",
            placeholder = "0"
        )

        TextFieldLayout(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = orderPrice,
            label = "Unit Price",
            placeholder = "0"
        )

        Box() {
            TextFieldDropDownLayout(
                placeholder = orderTypes[0],
                label = "Order",
                value = orderType.value,
                isExpanded = isOrderTypeExpanded,
                modifier = Modifier
                    .onGloballyPositioned {

                    }
            )
            DropDownMenuLayout(
                isExpanded = isOrderTypeExpanded,
                list = orderTypes,
                value = orderType,
            )
        }



        if (errorVisibility.value) {
            Text(text = "There is an empty field. Please try again", color = Imperial_Red)
        }
        Spacer(modifier = Modifier.size(spacerSize))
        Button(
            onClick = {

                if (orderAmount.value.isBlank() || orderPrice.value.isBlank()) {
                    errorVisibility.value = true
                } else {
                    viewModel.newOrder(
                        amount = orderAmount.value.toInt(),
                        price = orderPrice.value.toInt(),
                        item = viewModel.itemsMap.value[itemName.value]!!,
                        type = when (orderType.value) {
                            "Buy" -> OrderTypes.BUY
                            "Sell" -> OrderTypes.SELL
                            else -> OrderTypes.BUY
                        }
                    )
                    state.value = DialogState(
                        visibility = false,
                        type = DialogState.DialogType.NEW_ORDER
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Order")

        }
    }
}

@Composable
fun UpdateBalanceDialog(viewModel: HomeScreenViewModel, state: MutableState<DialogState>) {

    val text = remember { mutableStateOf(viewModel.balance.value.toString()) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
            TextFieldLayout(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = text,
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            )
            Button(
                onClick = {
                    viewModel.updateBalance(text.value.toInt())
                    state.value = DialogState(
                        visibility = false,
                        type = DialogState.DialogType.BALANCE
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                Text(text = "Update Balance")
            }
        }
    }

}

