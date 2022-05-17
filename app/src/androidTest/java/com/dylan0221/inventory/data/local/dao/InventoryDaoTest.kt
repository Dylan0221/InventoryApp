package com.dylan0221.inventory.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dylan0221.inventory.data.local.database.InventoryDatabase
import com.dylan0221.inventory.data.local.entities.AccountBalanceEntity
import com.dylan0221.inventory.data.local.entities.OrderEntity
import com.dylan0221.inventory.util.Constants.OrderTypes
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class InventoryDaoTest: TestCase() {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_database")
    lateinit var database: InventoryDatabase
    private lateinit var dao: InventoryDao


    @Before
    fun setup(){

        hiltAndroidRule.inject()

        dao = database.getInventoryDao()

    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertBuyOrder() = runBlocking {


        val order = OrderEntity(date = LocalDate.now(), item = 25, amount = 20, price = 20,id = 1, type = OrderTypes.BUY)

        dao.insertOrder(order)

        val checkOrder = dao.readAllOrders().first()




        assertThat(checkOrder[0]).isEqualTo(order)
    }

    @Test
    fun testReadWithoutData() = runBlocking {
        val list = dao.readAllItemEntity().first()

        assertThat(list).isEmpty()
    }

    @Test
    fun updateAccount() = runBlocking {
        val accountBalance = AccountBalanceEntity(id = 1, balance = 100, date = LocalDate.now())

        dao.insertAccountBalance(accountBalance)

        dao.updateAccountBalance(AccountBalanceEntity(id = 1, balance = 1000, date = accountBalance.date))

        val accounts = dao.readAllAccountBalance().first()

        assertThat(accounts[0]).isNotEqualTo(accountBalance)
    }

    @Test
    fun lastAccountBalance() = runBlocking {
        val balance1 = AccountBalanceEntity(id = 1, balance = 100, date = LocalDate.now())
        val balance2 = AccountBalanceEntity(id = 2, balance = 200, date = LocalDate.now())
        val balance3 = AccountBalanceEntity(id = 3, balance = 300, date = LocalDate.now())
        val balance4 = AccountBalanceEntity(id = 4, balance = 400, date = LocalDate.now())

        dao.insertAccountBalance(balance1)
        dao.insertAccountBalance(balance2)
        dao.insertAccountBalance(balance3)
        dao.insertAccountBalance(balance4)

        val lastBalance = dao.readLastAccountBalance().first()

        assertThat(lastBalance).isEqualTo(balance4)

    }

    @Test
    fun readAll() = runBlocking {
        val balance1 = AccountBalanceEntity(id = 1, balance = 100, date = LocalDate.now())
        val balance2 = AccountBalanceEntity(id = 2, balance = 200, date = LocalDate.now())
        val balance3 = AccountBalanceEntity(id = 3, balance = 300, date = LocalDate.now())
        val balance4 = AccountBalanceEntity(id = 4, balance = 400, date = LocalDate.now())

        dao.insertAccountBalance(balance1)
        dao.insertAccountBalance(balance2)
        dao.insertAccountBalance(balance3)
        dao.insertAccountBalance(balance4)

        val allBalance = dao.readAllAccountBalance().first()

        assertThat(allBalance).isEqualTo(listOf(balance1,balance2,balance3,balance4))
    }

    @Test
    fun getOrderByDateTest() = runBlocking {

        val date = LocalDate.of(2002, Month.JANUARY,30)


        val order1 = OrderEntity(
            id = 1,
            amount = 10,
            price = 10,
            date = LocalDate.of(2002, Month.JANUARY,30),
            item = 1,
            type = OrderTypes.BUY
        )
        val order2 = OrderEntity(
            id = 2,
            amount = 10,
            price = 10,
            date = LocalDate.of(2002, Month.JANUARY,30),
            item = 1,
            type = OrderTypes.BUY
        )
        val order3 = OrderEntity(
            id = 3,
            amount = 10,
            price = 10,
            date = LocalDate.of(2002, Month.FEBRUARY,1),
            item = 1,
            type = OrderTypes.BUY
        )
        val order4 = OrderEntity(
            id = 4,
            amount = 10,
            price = 10,
            date = LocalDate.of(2002, Month.FEBRUARY,2),
            item = 1,
            type = OrderTypes.BUY
        )
        val order5 = OrderEntity(
            id = 5,
            amount = 10,
            price = 10,
            date = LocalDate.of(2002, Month.FEBRUARY,3),
            item = 1,
            type = OrderTypes.BUY
        )

        val orderList: List<OrderEntity> = listOf(order1,order2,order3,order4,order5)

        for(i in orderList){
            dao.insertOrder(i)
        }


        val emmision = dao.readOrderByDate(date).first()

        assertThat(emmision).containsExactly(order1, order2).inOrder()
    }


}