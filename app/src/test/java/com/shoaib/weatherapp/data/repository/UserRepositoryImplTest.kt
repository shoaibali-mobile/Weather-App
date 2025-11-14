package com.shoaib.weatherapp.data.repository

import com.shoaib.weatherapp.data.local.dao.UserDao
import com.shoaib.weatherapp.data.local.entity.UserEntity
import com.shoaib.weatherapp.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    private lateinit var userDao: UserDao
    private lateinit var repository: UserRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userDao = mock()
        repository = UserRepositoryImpl(userDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSignUpSuccess() = runTest {
        val user = User(
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            password = "password123"
        )

        val userId = 1L


        whenever(userDao.insertUser(any<UserEntity>()))
            .thenReturn(userId)


        val result = repository.signUp(user)


        assertTrue(result.isSuccess)
        assertEquals(userId, result.getOrNull())

        argumentCaptor<UserEntity>().apply {
            verify(userDao).insertUser(capture())

            val savedEntity = firstValue
            assertEquals("John", savedEntity.firstName)
            assertEquals("Doe", savedEntity.lastName)
            assertEquals("john.doe@example.com", savedEntity.email)
            assertEquals("password123", savedEntity.password)
        }
    }

    @Test
    fun testLoginSuccess() = runTest {

        val email = "john.doe@example.com"
        val password = "password123"

        val userEntity = UserEntity(
            id = 1L,
            firstName = "John",
            lastName = "Doe",
            email = email,
            password = password
        )


        whenever(userDao.getUserByEmailAndPassword(email, password))
            .thenReturn(userEntity)

        val result = repository.login(email, password)


        assertTrue(result.isSuccess)

        val user = result.getOrNull()
        assertNotNull(user)
        assertEquals(1L, user?.id)
        assertEquals("John", user?.firstName)
        assertEquals("Doe", user?.lastName)
        assertEquals(email, user?.email)
        assertEquals(password, user?.password)

        verify(userDao).getUserByEmailAndPassword(email, password)
    }
}