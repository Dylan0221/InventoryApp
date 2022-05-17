package com.dylan0221.inventory.domain.usecases

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.ItemEntity
import com.dylan0221.inventory.domain.model.ItemModel
import com.dylan0221.inventory.domain.model.OrderModel
import com.google.common.truth.Truth.assertThat
import com.dylan0221.inventory.domain.repository.InventoryRepository
import com.dylan0221.inventory.domain.usecases.usecase_packages.AllUseCases
import com.dylan0221.inventory.util.Constants.OrderTypes
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class UseCasesTest{
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_repo")
    lateinit var repository: InventoryRepository

    private lateinit var getAllItemsUC: GetAllItemsUseCase
    private lateinit var updateBalanceUC: UpdateBalanceUseCase
    private lateinit var getCurrentBalanceUC: GetCurrentBalanceUseCase
    private lateinit var newOrderUC: NewOrderUseCase

    private val item1 = ItemEntity(id = 1, name = "prueba1", amount = 10)
    private val item2 = ItemEntity(id = 2, name = "prueba2", amount = 10)
    private val item3 = ItemEntity(id = 3, name = "prueba3", amount = 10)
    private val balance1 = AccountBalanceEntity(id = 1, balance = 100, date = LocalDate.now())
    private val balance2 = AccountBalanceEntity(id = 2, balance = 200, date = LocalDate.now())
    private val balance3 = AccountBalanceEntity(id = 3, balance = 300, date = LocalDate.now())

    private val balanceList: List<AccountBalanceEntity> = listOf(balance1, balance2, balance3)
    private val itemList: List<ItemEntity> = listOf(item1,item2,item3)


    @Before
    fun setup(){


        hiltAndroidRule.inject()

        getAllItemsUC = GetAllItemsUseCase(repository)
        updateBalanceUC = UpdateBalanceUseCase(repository)
        getCurrentBalanceUC = GetCurrentBalanceUseCase(repository)
        newOrderUC = NewOrderUseCase(repository)


        runBlocking {
            for (i in itemList) {
                repository.newItem(i)
            }
            for(i in balanceList){
                repository.newAccountBalance(i)
            }
        }
    }

    @Test
    fun getAllItemsTest() = runBlocking {
        val list = itemList.map { it.toModel() }
        getAllItemsUC().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(list)

        }

    }

    @Test
    fun updateBalanceUC() = runBlocking {

        updateBalanceUC(500)

        val newbalance = repository.getLastAccountBalance().first()


        assertThat(newbalance.balance).isEqualTo(500)
    }


    @Test
    fun getCurrentBalanceTest() = runBlocking {

        getCurrentBalanceUC().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(300)
        }
    }

    @Test
    fun newOrderTypeBuyReducesBalanceTest() = runBlocking {
        val order = OrderModel(
            id = 0,
            type = OrderTypes.BUY,
            date = LocalDate.now(),
            amount = 5,
            price = 5,
            item = 1
        )

        val oldBalance = repository.getLastAccountBalance().first()

        newOrderUC(item1.id,order.price,order.amount,order.type)

        val newBalanceUseCase = repository.getLastAccountBalance().first()

        assertThat(oldBalance.balance).isGreaterThan(newBalanceUseCase.balance)
    }

    @Test
    fun newOrderTypeSellIncreasesBalanceTest() = runBlocking {
        val order = OrderModel(
            id = 0,
            type = OrderTypes.SELL,
            date = LocalDate.now(),
            amount = 5,
            price = 5,
            item = 1
        )

        val oldBalance = repository.getLastAccountBalance().first()

        newOrderUC(item1.id,order.price,order.amount,order.type)

        val newBalanceUseCase = repository.getLastAccountBalance().first()

        assertThat(oldBalance.balance).isLessThan(newBalanceUseCase.balance)
    }

    @Test
    fun newOrderTypeBuyIncreasesItemAmountTest() = runBlocking {
        val order = OrderModel(
            id = 0,
            type = OrderTypes.BUY,
            date = LocalDate.now(),
            amount = 5,
            price = 5,
            item = 1
        )

        val oldItem = item1

        newOrderUC(oldItem.id,order.price,order.amount,order.type)

        val newItem = repository.getItemById(oldItem.id).first()

        assertThat(oldItem.amount).isLessThan(newItem.amount)
    }

    @Test
    fun newOrderTypeSellReducesItemAmountTest() = runBlocking {
        val order = OrderModel(
            id = 0,
            type = OrderTypes.SELL,
            date = LocalDate.now(),
            amount = 5,
            price = 5,
            item = 1
        )

        val oldItem = item1

        newOrderUC(oldItem.id,order.price,order.amount,order.type)

        val newItem = repository.getItemById(oldItem.id).first()

        assertThat(oldItem.amount).isGreaterThan(newItem.amount)
    }


}